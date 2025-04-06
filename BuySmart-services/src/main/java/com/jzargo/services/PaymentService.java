package com.jzargo.services;

import com.jzargo.dto.PaymentCreateAndUpdateDto;
import com.jzargo.dto.PaymentReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Page<PaymentReadDto> findAllByUserId(Long userId, Pageable page);
    PaymentReadDto findById(Long id);
    String createCheckoutSession(Long userId, PaymentCreateAndUpdateDto dto);
}
