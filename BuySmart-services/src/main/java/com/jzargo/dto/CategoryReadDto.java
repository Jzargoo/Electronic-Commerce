package com.jzargo.dto;

import com.jzargo.common.Categories;
import com.jzargo.entity.Coupon;
import com.jzargo.entity.SeasonDiscount;
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
    private List<Coupon> coupons;
    private SeasonDiscount seasonDiscounts;
}

