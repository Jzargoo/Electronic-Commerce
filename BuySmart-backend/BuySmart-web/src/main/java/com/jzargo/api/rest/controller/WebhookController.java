package com.jzargo.api.rest.controller;

import com.jzargo.shared.model.PaymentReadDto;
import com.jzargo.exceptions.PaymentException;
import com.jzargo.services.PaymentService;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Service
@RequestMapping("/api/webhook")
public class WebhookController {
    private final PaymentService paymentService;
    @Value("${stripe.webhook.secret}")
    private String secret;

    public WebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<?> handleStripeWebhook(String payload, String signature) {
        Event event;

        if (!secret.equals("dummy") && signature != null) {
            try {
                event = Webhook.constructEvent(payload, signature, secret);
            } catch (Exception e) {
                log.error("Error verifying event: {}", e.getMessage());
                return ResponseEntity.notFound().build();
            }

            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            }

            switch (event.getType()) {

                case "payment_intent.succeeded" -> {
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    if (paymentIntent == null) {
                        throw new PaymentException("Response is null");
                    }

                    log.info("Payment for {} succeeded.", paymentIntent.getAmount());
                    PaymentReadDto payment = paymentService.createPayment(paymentIntent);
                    return ResponseEntity.ok(payment);

                }

                case "payment_intent.payment_failed" -> {
                    PaymentIntent pi = (PaymentIntent) stripeObject;
                    if (pi == null) {
                        throw new PaymentException("Response is null");
                    }
                    log.warn("Payment failed: {}", pi.getLastPaymentError() != null ? pi.getLastPaymentError().getMessage() : "Unknown error");
                    paymentService.handleFailedPayment(pi);
                }


                case "payment_method.attached"->{
                    PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                    if (paymentMethod== null) {
                        throw new PaymentException("Response is null");
                    }

                    log.info("User data's successfully posted to stripe");
                    paymentService.createCustomer(paymentMethod);
                    return ResponseEntity.ok().build();

                }

                case "charge.refunded" -> {
                    Charge charge = (Charge) stripeObject;
                    if (charge != null) {
                        log.info("🔄 Charge {} refunded (amount: {})", charge.getId(), charge.getAmountRefunded());
                    }
                    paymentService.handleRefund(charge);
                }

                default -> {
                    log.error("Unhandled event type: {}", event.getType());
                    return ResponseEntity.badRequest().build();

                }

            }
        }
        // This never happen thanks switch
        return null;
    }
}
