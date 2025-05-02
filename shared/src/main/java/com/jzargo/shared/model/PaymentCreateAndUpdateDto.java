package com.jzargo.shared.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCreateAndUpdateDto {
    private Long orderId;
    private Double amount;
    private String currency;
}
