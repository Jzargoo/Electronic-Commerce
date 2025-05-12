package com.jzargo.services;

import com.jzargo.common.QPredicate;
import com.jzargo.entity.QReview;
import com.jzargo.entity.Review;
import com.jzargo.filtration.ReviewFilter;
import com.jzargo.mapper.ReviewReadMapper;
import com.jzargo.repository.ProductRepository;
import com.jzargo.repository.ReviewRepository;
import com.jzargo.repository.UserRepository;
import com.jzargo.shared.model.ReviewCreateAndUpdateDto;
import com.jzargo.shared.model.ReviewReadDto;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewReadMapper reviewReadMapper;

    public ReviewServiceImpl(UserRepository userRepository, ProductRepository productRepository,
                             ReviewRepository reviewRepository, ReviewReadMapper reviewReadMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.reviewReadMapper = reviewReadMapper;
    }

    @Override
    public ReviewReadDto addReview(Integer productId, Long userId, ReviewCreateAndUpdateDto reviewCreateAndUpdateDto) {
        Review build = Review.builder()
                .comment(reviewCreateAndUpdateDto.getMessage())
                .user(
                        userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"))
                )
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .product(
                        productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"))
                )
                .rating(reviewCreateAndUpdateDto.getRating())
                .build();

        Review review = reviewRepository.saveAndFlush(build);
        return reviewReadMapper.map(review);
    }

    @Override
    public ReviewReadDto updateReview(Long reviewId, ReviewCreateAndUpdateDto reviewCreateAndUpdateDto) {
        Review savedReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        Review build = Review.builder()
                .comment(reviewCreateAndUpdateDto.getMessage())
                .user(
                        userRepository.findById(savedReview.getUser().getId())
                                .orElseThrow(() -> new RuntimeException("User not found"))
                )
                .updatedAt(LocalDateTime.now())
                .createdAt(savedReview.getCreatedAt())
                .product(
                        productRepository.findById(savedReview.getProduct().getId())
                                .orElseThrow(() -> new RuntimeException("Product not found"))
                )
                .rating(reviewCreateAndUpdateDto.getRating())
                .build();

        return reviewReadMapper.map(build);
    }

    @Override
    public Boolean deleteReview(Long reviewId) {
        reviewRepository.delete(
                reviewRepository.findById(reviewId).orElseThrow()
        );
        return reviewRepository.existsById(reviewId);
    }

    @Override
    public Page<ReviewReadDto> getReviewsByProductId(ReviewFilter reviewFilter,
                                                     Pageable pageable) {
        Predicate predicate = QPredicate.builder()
                .add(reviewFilter.rating(), QReview.review.rating::eq)
                .add(reviewFilter.productId(), QReview.review.product.id::eq)
                .add(reviewFilter.fromDate(), QReview.review.createdAt::goe)
                .add(reviewFilter.toDate(), QReview.review.createdAt::loe)
                .add(reviewFilter.userId(), QReview.review.user.id::eq)
                .add(reviewFilter.withTextOnly(), value ->
                        value != null && value ? QReview.review.comment.isNotEmpty() : null)
                .add(reviewFilter.withImagesOnly(), value ->
                        value != null && value ? QReview.review.images.isNotEmpty() : null)
                .buildAnd();

        return reviewRepository.findAll(predicate, pageable)
                .map(reviewReadMapper::map);
    }

    @Override
    public Page<ReviewReadDto> getReviewsByUserId(Long userId, Pageable pageable) {
        Predicate predicate = QPredicate.builder()
                .add(userId, QReview.review.user.id::eq)
                .buildAnd();
        return reviewRepository.findAll(predicate,pageable)
                .map(reviewReadMapper::map);
    }

    @Override
    public ReviewReadDto getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(reviewReadMapper::map)
                .orElseThrow();
    }
}