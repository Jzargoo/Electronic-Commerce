package com.jzargo.dto;

import com.jzargo.common.Tags;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCreateAndUpdateDto {
    private LocalDateTime created;
    @Size(min = 1, message = "Tags cannot be empty")
    private List<Tags> tags;
    @Size(min = 1, message = "At least one image should be uploaded")
    private List<MultipartFile> images;
    @NotBlank(message = "Product name cannot be empty")
    private String name;
    private String description;
    @NotNull(message = "Product price cannot be null")
    @Positive(message = "Price must be a positive number")
    private Float price;
}
