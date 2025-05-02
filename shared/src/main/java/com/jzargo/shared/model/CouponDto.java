package com.jzargo.shared.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class CouponDto extends DiscountDto{
    private String couponCode;
    private int categoryId;
}