package com.jzargo.mapper;

import com.jzargo.dto.CouponDto;
import com.jzargo.dto.ProductDiscountDto;
import com.jzargo.dto.SeasonDiscountDto;
import com.jzargo.entity.Coupon;
import com.jzargo.entity.ProductDiscount;
import com.jzargo.entity.SeasonDiscount;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.repository.CategoryRepository;
import com.jzargo.repository.ProductRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

public class DiscountCreateMapper {
    @Component
    public static class CouponCreateMapper implements Mapper<CouponDto, Coupon> {
        private final CategoryRepository categoryRepository;

        public CouponCreateMapper(CategoryRepository categoryRepository) {
            this.categoryRepository = categoryRepository;
        }

        @Override
        @SneakyThrows
        public Coupon map(CouponDto object) {
            return (Coupon) Coupon.builder()
                    .category(
                        categoryRepository.findById(object.getCategoryId())
                            .orElseThrow(() -> new DataNotFoundException("Category not found"))
                    )
                    .code(object.getCouponCode())
                    .minOrderPrice(object.getMinOrderPrice())
                    .startDate(object.getStartDate())
                    .endDate(object.getEndDate())
                    .discount(object.getDiscount())
                    .discountType(object.getDiscountType())
                    .build();
        }
    }

    @Component
    public static class SeasonDiscountCreateMapper implements Mapper<SeasonDiscountDto, SeasonDiscount> {
        @Override
        public SeasonDiscount map(SeasonDiscountDto object) {
            return (SeasonDiscount) SeasonDiscount.builder()
                    .discount(object.getDiscount())
                    .discountType(object.getDiscountType())
                    .startDate(object.getStartDate())
                    .endDate(object.getEndDate())
                    .minOrderPrice(object.getMinOrderPrice())
                    .build();
        }
    }

    @Component
    public static class ProductDiscountCreateMapper implements Mapper<ProductDiscountDto, ProductDiscount> {
        private final ProductRepository productRepository;

        public ProductDiscountCreateMapper(ProductRepository productRepository1) {
            this.productRepository = productRepository1;
        }

        @Override
        @SneakyThrows
        public ProductDiscount map(ProductDiscountDto object) {
            return (ProductDiscount) ProductDiscount.builder()
                    .product(
                            productRepository.findById(object.getProductId())
                                    .orElseThrow(() -> new DataNotFoundException("Product not found"))
                    )
                    .discount(object.getDiscount())
                    .discountType(object.getDiscountType())
                    .startDate(object.getStartDate())
                    .endDate(object.getEndDate())
                    .minOrderPrice(object.getMinOrderPrice())
                    .build();
        }
    }
}