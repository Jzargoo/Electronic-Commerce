package com.jzargo.mapper;

import com.jzargo.dto.ProductReadDto;
import com.jzargo.entity.Product;
import com.jzargo.entity.Review;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    @Override
    public ProductReadDto map(Product object) {
        return ProductReadDto.builder()
                .tags(object.getTags())
                .id(object.getId())
                .reviewId(object.getReviews().stream()
                        .min(Comparator.comparingDouble(Review::getRating))
                        .map(Review::getId)
                        .orElse(null)
                )
                .images(object.getImages())
                .price(object.getPrice())
                .name(object.getName())
                .description(object.getDescription())
                .created(object.getCreated())
                .build();
    }
}
