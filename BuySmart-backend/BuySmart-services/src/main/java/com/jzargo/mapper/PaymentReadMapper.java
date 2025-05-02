package com.jzargo.mapper;

import com.jzargo.shared.model.PaymentReadDto;
import com.jzargo.entity.Payment;
import com.jzargo.exceptions.DataNotFoundException;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PaymentReadMapper implements Mapper<Payment, PaymentReadDto>{
    @Value("${Stripe.api.key}")
    private String stripeApiKey;

    @Override
    @SneakyThrows
    public PaymentReadDto map(Payment object){
        return PaymentReadDto.builder()
                .id(object.getId())
                .checkoutUrl(
                        fetchUrl(object.getStripePaymentId())
                )
                .currency(object.getCurrency())
                .amount(object.getAmount())
                .updatedAt(object.getUpdatedAt())
                .createdAt(object.getCreatedAt())
                .status(object.getStatus())
                .orderId(object.getId())
                .paymentMethod(object.getPaymentMethod().getType())
                .build();
    }

    @SneakyThrows
    private String fetchUrl(String stripePaymentId){
        if(stripePaymentId==null) throw new DataNotFoundException("cannot find payment for this order");
        Stripe.apiKey = stripeApiKey;
        Session session=Session.retrieve(stripePaymentId);
        return session.getUrl();
    }
}