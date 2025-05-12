package com.jzargo.shared.model;

import com.jzargo.shared.common.Tags;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDetails {
    private Integer id;
    private LocalDateTime created;
    private List<Tags> tags;
    private List<String> images;
    private String name;
    private String description;
    private Double price;
    private Long reviewId;
}
