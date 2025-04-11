package com.jzargo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
@Builder
public class ReviewCreateAndUpdateDto {
    @NotNull
    private Integer rating;
    @NotNull
    private Long productId;
    private String message;
    private List<MultipartFile> images;
}
