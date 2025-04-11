package com.jzargo.mapper;

import com.jzargo.dto.ReviewReadDto;
import com.jzargo.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewReadMapper implements Mapper<Review, ReviewReadDto>{
    @Override
    public ReviewReadDto map(Review object) {
        return ReviewReadDto.builder()
                .id(object.getId())
                .message(object.getComment())
                .Rating(object.getRating())
                .productId(object.getProduct().getId())
                .userName(object.getUser().getUsername())
                .build();
    }
}
