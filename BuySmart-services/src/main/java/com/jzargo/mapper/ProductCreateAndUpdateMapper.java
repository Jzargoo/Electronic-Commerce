package com.jzargo.mapper;

import com.jzargo.common.Categories;
import com.jzargo.common.Tags;
import com.jzargo.dto.ProductCreateAndUpdateDto;
import com.jzargo.entity.Product;
import com.jzargo.repository.CategoryRepository;
import com.jzargo.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductCreateAndUpdateMapper implements Mapper<ProductCreateAndUpdateDto, Product> {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ProductCreateAndUpdateMapper(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
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
                .category(
                        categoryRepository.findByCategory(
                                Categories.valueOf(object.getCategory()))
                )
                .tags(
                        object.getTags().stream()
                                .map(Tags::valueOf).toList()

                )
                .build();
    }
}