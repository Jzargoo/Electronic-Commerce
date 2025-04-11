package com.jzargo.dto;

import lombok.Builder;

@Builder
public class ReviewReadDto {
    private Long id;
    private Integer Rating;
    private String message;
    private String userName;
    private Integer productId;
}
