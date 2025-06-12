package com.jzargo.mapper;

import com.jzargo.entity.Product;
import com.jzargo.entity.Review;
import com.jzargo.shared.model.ProductDetails;
import com.jzargo.shared.model.ProductReadDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    @Override
    public ProductReadDto map(Product object) {
        return ProductReadDto.builder()
                .id(object.getId())
                .image(object.getImages().getFirst())
                .price(object.getPrice())
                .name(object.getName())
                .build();
    }
    @Component
    public static class ProductDetailsReadMapper {
        public static ProductDetails map(Product object) {
            return ProductDetails.builder()
                    .tags(object.getTags())
                    .id(object.getId())
                    .reviewId(Collections.singletonList(object.getReviews().stream()
                            .min(Comparator.comparingDouble(Review::getRating))
                            .map(Review::getId)
                            .orElse(null))
                    )
                    .images(object.getImages())
                    .price(object.getPrice())
                    .name(object.getName())
                    .description(object.getDescription())
                    .created(object.getCreated())
                    .build();
        }
    }
}
