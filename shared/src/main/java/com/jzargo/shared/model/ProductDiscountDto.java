package com.jzargo.shared.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ProductDiscountDto extends DiscountDto{
    private Integer productId;
}
