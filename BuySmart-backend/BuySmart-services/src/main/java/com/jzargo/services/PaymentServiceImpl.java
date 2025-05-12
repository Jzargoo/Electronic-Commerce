package com.jzargo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzargo.entity.Order;
import com.jzargo.entity.Payment;
import com.jzargo.entity.PaymentMethod;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.exceptions.MetadataException;
import com.jzargo.exceptions.PaymentException;
import com.jzargo.mapper.PaymentCreateAndUpdateMapper;
import com.jzargo.mapper.PaymentReadMapper;
import com.jzargo.repository.OrderRepository;
import com.jzargo.repository.PaymentMethodRepository;
import com.jzargo.repository.PaymentRepository;
import com.jzargo.repository.UserRepository;
import com.jzargo.shared.common.PaymentStatus;
import com.jzargo.shared.common.PaymentType;
import com.jzargo.shared.model.PaymentCreateAndUpdateDto;
import com.jzargo.shared.model.PaymentReadDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService{
    private final PaymentMethodRepository paymentMethodRepository;
    @Value("${stripe.api.key}")
    private String apiKey;
    private final PaymentReadMapper paymentReadMapper;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentCreateAndUpdateMapper paymentCreateAndUpdateMapper;

    public PaymentServiceImpl(PaymentReadMapper paymentReadMapper, PaymentRepository paymentRepository, UserRepository userRepository, OrderRepository orderRepository, PaymentCreateAndUpdateMapper paymentCreateAndUpdateMapper, PaymentMethodRepository paymentMethodRepository){
        this.paymentReadMapper = paymentReadMapper;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.paymentCreateAndUpdateMapper = paymentCreateAndUpdateMapper;
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public Page<PaymentReadDto> findAllByUserId(Long userId, Pageable page) {
        return paymentRepository.findAllByUserId(userId, page)
                .map(paymentReadMapper::map);
    }

    @Override
    public PaymentReadDto findById(Long id) {
        return paymentRepository.findById(id)
                .map(paymentReadMapper::map)
                .orElseThrow();
    }

    @Override
    @SneakyThrows
    public String createCheckoutSession(Long userId, PaymentCreateAndUpdateDto dto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Stripe.apiKey= apiKey;

        if(user.getStripeCustomerId() == null){
            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setEmail(user.getEmail())
                    .setName(user.getUsername())
                    .build();
            Customer customer = Customer.create(customerParams);
            user.setStripeCustomerId(customer.getId());
        }

        SessionCreateParams order = SessionCreateParams.builder().addAllPaymentMethodType(List.of(
                        SessionCreateParams.PaymentMethodType.CARD,
                        SessionCreateParams.PaymentMethodType.PAYPAL,
                        SessionCreateParams.PaymentMethodType.PAY_BY_BANK)
                )
                .setCustomer(user.getStripeCustomerId())
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/order/success/" + dto.getOrderId())
                .setCancelUrl("http://localhost:3000/order/cancel/" + dto.getOrderId())
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(
                                (long) orderRepository.findById(dto.getOrderId())
                                        .map(Order::getQuantity)
                                        .orElseThrow()
                        )
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(dto.getCurrency())
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName("Order")
                                                        .build())
                                        .setUnitAmount((long) (dto.getAmount() * 100.0))
                                        .build()
                        )
                        .build()
                )
                .build();
        Session session = Session.create(order);
        var payment = paymentCreateAndUpdateMapper.map(dto);
        payment.setUser(user);
        payment.setStripePaymentId(session.getId());

        paymentRepository.saveAndFlush(payment);
        return session.getUrl();
    }

    @Override
    public PaymentReadDto createPayment(PaymentIntent intent) {

        Payment payment = paymentRepository.findByStripePaymentId(intent.getId()).orElseThrow();
        ObjectMapper objectMapper = new ObjectMapper();
        String metadataJson;

        try {
            metadataJson = objectMapper.writeValueAsString(intent.getMetadata());
        } catch (JsonProcessingException e) {
            throw new MetadataException(e.getMessage());
        }

        payment.setMetadata(metadataJson);

        try {
            payment.setStatus(PaymentStatus.valueOf(intent.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown payment status from Stripe: " + intent.getStatus());
        }

        return Optional.of(
                paymentRepository.saveAndFlush(payment)
        ).map(paymentReadMapper::map).orElseThrow();
    }

    @SneakyThrows
    @Override
    public PaymentMethod createCustomer(com.stripe.model.PaymentMethod paymentMethod) {
        String id = paymentMethod.getId();
        String email = paymentMethod.getBillingDetails().getEmail();
        PaymentMethod build = PaymentMethod.builder()
                .stripeId(id)
                .createdAt(LocalDateTime.now())
                .user(
                        userRepository.getUserByEmail(email)
                )
                .build();

        switch ( paymentMethod.getType()) {

            case "card" -> {
                build.setBrand(paymentMethod.getCard().getBrand());
                build.setLast4(paymentMethod.getCard().getLast4());
                build.setType(PaymentType.CARD);
            }

            case "paypal" -> {
                build.setPaypalAccount(paymentMethod.getPaypal().getPayerId());
                build.setType(PaymentType.PAYPAL);

            }

            case "us_bank_account" -> {
                build.setBrand(
                        paymentMethod.getUsBankAccount().getBankName()
                );
                build.setLast4(
                        paymentMethod.getUsBankAccount().getLast4()
                );
                build.setType(PaymentType.US_BANK_ACCOUNT);
            }
            default -> throw new PaymentException("Cannot support that payment type");
        }
        return paymentMethodRepository.saveAndFlush(build);
    }

    @SneakyThrows
    @Override
    public boolean handleFailedPayment(PaymentIntent pi) {
        Payment payment = paymentRepository.findByStripePaymentId(pi.getId()).orElseThrow(
                () -> new PaymentException("Cannot find payment with " + pi.getId())
        );
        paymentRepository.delete(payment);
        orderRepository.delete(payment.getOrder());
        return paymentRepository.existsByStripePaymentId(payment);
    }

    @Override
    public void handleRefund(Charge charge) {
        if (charge == null) {
            throw new IllegalArgumentException("Charge can't be null");
        }

        if (!charge.getRefunded()) {
            log.warn("Refund doesnt exist");
            return;
        }

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("charge", charge.getId());

            RefundCollection refunds = Refund.list(params);

            for (Refund refund : refunds.getData()) {
                System.out.println("Refund ID: " + refund.getId());
                System.out.println("Amount: " + refund.getAmount());
                System.out.println("Status: " + refund.getStatus());

                Payment payment = paymentRepository.findByStripePaymentId(refund.getId()).orElseThrow();
                payment.setStatus(PaymentStatus.REFUNDED);
                paymentRepository.saveAndFlush(payment);

                Order order = orderRepository.findByPayment(payment).orElseThrow();
                orderRepository.delete(order);
            }

        } catch (StripeException e) {
            log.error("Error with refund");
        }
    }
}
