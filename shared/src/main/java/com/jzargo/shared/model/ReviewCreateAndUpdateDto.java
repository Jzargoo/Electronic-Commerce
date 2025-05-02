package com.jzargo.shared.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ReviewCreateAndUpdateDto {
    @NotNull
    private Integer rating;
    @NotNull
    private Long productId;
    private String message;
    private List<byte[]> images;
}
