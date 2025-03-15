package com.jzargo.mapper;

import com.jzargo.dto.ProductCreateAndUpdateDto;
import com.jzargo.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateAndUpdateMapper implements Mapper<ProductCreateAndUpdateDto, Product> {
    @Override
    public Product map(ProductCreateAndUpdateDto object) {
        return Product.builder()
                .created(object.getCreated())
                .price(object.getPrice())
                .description(object.getDescription())
                .tags(object.getTags())
                .build();
    }
}