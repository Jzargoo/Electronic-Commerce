package com.jzargo.mapper;

import com.jzargo.dto.ProductCreateAndUpdateDto;
import com.jzargo.entity.Product;
import com.jzargo.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductCreateAndUpdateMapper implements Mapper<ProductCreateAndUpdateDto, Product> {
    private final UserRepository userRepository;

    public ProductCreateAndUpdateMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Product map(ProductCreateAndUpdateDto object) {
        return Product.builder()
                .created(LocalDateTime.now())
                .name(object.getName())
                .user(
                        userRepository.findById(object.getUserId()).orElseThrow()
                )
                .price(object.getPrice())
                .description(object.getDescription())
                .tags(object.getTags())
                .build();
    }
}