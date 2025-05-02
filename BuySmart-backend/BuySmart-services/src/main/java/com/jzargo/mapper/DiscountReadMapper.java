package com.jzargo.mapper;

import com.jzargo.shared.model.CouponDto;
import com.jzargo.shared.model.DiscountDto;
import com.jzargo.shared.model.ProductDiscountDto;
import com.jzargo.shared.model.SeasonDiscountDto;
import com.jzargo.entity.Coupon;
import com.jzargo.entity.Discount;
import com.jzargo.entity.ProductDiscount;
import com.jzargo.entity.SeasonDiscount;
import org.springframework.stereotype.Component;

@Component
public class DiscountReadMapper implements Mapper<Discount, DiscountDto> {
    @Override
    public DiscountDto map(Discount object) {
        return DiscountDto.builder()
                .minOrderPrice(object.getMinOrderPrice())
                .discountType(object.getDiscountType())
                .discount(object.getDiscount())
                .endDate(object.getEndDate())
                .startDate(object.getStartDate())
                .build();
    }

    @Component
    public static class CouponReadMapper implements Mapper<Coupon, CouponDto> {
        @Override
        public CouponDto map(Coupon object) {
            return CouponDto.builder()
                    .couponCode(object.getCode())
                    .discount(object.getDiscount())
                    .discountType(object.getDiscountType())
                    .startDate(object.getStartDate())
                    .endDate(object.getEndDate())
                    .minOrderPrice(object.getMinOrderPrice())
                    .categoryId(object.getCategory().getId())
                    .build();
        }
    }
    @Component
    public static class ProductDiscountReadMapper implements Mapper<ProductDiscount, ProductDiscountDto> {

        @Override
        public ProductDiscountDto map(ProductDiscount object) {
            return ProductDiscountDto.builder()
                    .minOrderPrice(object.getMinOrderPrice())
                    .discount(object.getDiscount())
                    .discountType(object.getDiscountType())
                    .productId(object.getProduct().getId())
                    .endDate(object.getEndDate())
                    .startDate(object.getStartDate())
                    .build();
        }
    }
    @Component
    public static class SeasonDiscountReadMapper implements Mapper<SeasonDiscount, SeasonDiscountDto> {
        @Override
        public SeasonDiscountDto map(SeasonDiscount object) {
            return SeasonDiscountDto.builder()
                    .minOrderPrice(object.getMinOrderPrice())
                    .discount(object.getDiscount())
                    .discountType(object.getDiscountType())
                    .categoryId(object.getCategory().getId())
                    .endDate(object.getEndDate())
                    .startDate(object.getStartDate())
                    .build();
        }
    }
}
