package com.jzargo.services;


import com.jzargo.filtration.ReviewFilter;
import com.jzargo.shared.model.ReviewCreateAndUpdateDto;
import com.jzargo.shared.model.ReviewReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReviewService {
    ReviewReadDto addReview(Integer productId, Long userId,  ReviewCreateAndUpdateDto reviewCreateAndUpdateDto);
    ReviewReadDto updateReview(Long reviewId, ReviewCreateAndUpdateDto reviewCreateAndUpdateDto);
    Boolean deleteReview(Long reviewId);
    Page<ReviewReadDto> getReviewsByProductId( ReviewFilter reviewFilter, Pageable pageable);
    Page<ReviewReadDto> getReviewsByUserId(Long userId, Pageable pageable);
    ReviewReadDto getReviewById(Long reviewId);
}
