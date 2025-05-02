package com.jzargo.services;

import com.jzargo.shared.model.PaymentCreateAndUpdateDto;
import com.jzargo.shared.model.PaymentReadDto;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Page<PaymentReadDto> findAllByUserId(Long userId, Pageable page);
    PaymentReadDto findById(Long id);
    String createCheckoutSession(Long userId, PaymentCreateAndUpdateDto dto);
    PaymentReadDto createPayment(PaymentIntent intent);

    com.jzargo.entity.PaymentMethod createCustomer(PaymentMethod paymentMethod);

    boolean handleFailedPayment(PaymentIntent pi);

    void handleRefund(Charge charge);
}
