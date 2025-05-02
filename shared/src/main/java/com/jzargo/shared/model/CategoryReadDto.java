package com.jzargo.shared.model;

import com.jzargo.shared.common.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReadDto {
    private Categories categories;
    private List<CouponDto> coupons;
    private SeasonDiscountDto seasonDiscounts;
}

