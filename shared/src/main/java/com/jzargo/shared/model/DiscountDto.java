package com.jzargo.shared.model;

import com.jzargo.shared.common.DiscountType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@Data
@SuperBuilder
public class DiscountDto {
    private Double discount;
    private DiscountType discountType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double minOrderPrice;
}
