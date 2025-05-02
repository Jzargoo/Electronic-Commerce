package com.jzargo.shared.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SeasonDiscountDto extends DiscountDto{
    private Integer categoryId;
}
