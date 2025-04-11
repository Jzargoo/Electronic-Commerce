package com.jzargo.api.rest.controller;

import com.google.gson.JsonSyntaxException;
import com.jzargo.services.PaymentService;
import com.stripe.model.*;
import com.stripe.net.ApiResource;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<?> handleStripeWebhook(String payload, String signature, String secret) {
        Event event = null;

        if (!secret.equals("dummy") && signature != null) {
            try {
                event = Webhook.constructEvent(payload, signature, secret);
            } catch (Exception e) {
                System.out.println("Error verifying event: " + e.getMessage());
                return ResponseEntity.notFound().build();
            }

            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            }
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded.");
                    paymentService.createPayment(paymentIntent);
                    break;
                case "payment_method.attached":
                    PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
                    // handlePaymentMethodAttached(paymentMethod);
                    break;
                default:
                    System.out.println("Unhandled event type: " + event.getType());
                    break;
            }
        }
    }
}
