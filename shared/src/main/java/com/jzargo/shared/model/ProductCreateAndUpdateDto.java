package com.jzargo.shared.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCreateAndUpdateDto {
    @Size(min = 1, message = "Tags cannot be empty")
    private List<String> tags;
    @Size(min = 1, message = "At least one image should be uploaded")
    private List<byte[]> images;
    @NotBlank(message = "Product name cannot be empty")
    private String name;
    @NotNull
    private Long userId;
    private String description;
    @NotNull(message = "Product price cannot be null")
    @Positive(message = "Price must be a positive number")
    private Double price;
    private String category;
}
