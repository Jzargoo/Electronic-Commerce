package com.jzargo.mapper;

import com.jzargo.dto.ProductReadDto;
import com.jzargo.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    @Override
    public ProductReadDto map(Product object) {
        return ProductReadDto.builder()
                .tags(object.getTags())
                .id(object.getId())
                .images(object.getImages())
                .price(object.getPrice())
                .name(object.getName())
                .description(object.getDescription())
                .created(object.getCreated())
                .build();
    }
}
