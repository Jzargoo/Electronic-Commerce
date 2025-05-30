package com.jzargo.shared.model;

import com.jzargo.shared.common.PaymentStatus;
import com.jzargo.shared.common.PaymentType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PaymentReadDto {
    private Long id;
    private Long orderId;
    private Long userId;
    private PaymentType paymentMethod;
    private PaymentStatus status;
    private Double amount;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String checkoutUrl;
}
