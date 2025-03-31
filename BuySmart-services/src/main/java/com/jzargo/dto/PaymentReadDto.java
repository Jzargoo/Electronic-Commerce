package com.jzargo.dto;

import com.jzargo.common.PaymentStatus;
import com.jzargo.common.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Currency;

@Builder
@Data
public class PaymentReadDto {
    private Long id;
    private Long orderId;
    private PaymentType paymentMethod;
    private PaymentStatus status;
    private Long amount;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String checkoutUrl;
}
