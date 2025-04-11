package com.jzargo.dto;

import com.jzargo.common.Tags;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ProductReadDto {
    private Integer id;
    private LocalDateTime created;
    private List<Tags> tags;
    private List<String> images;
    private String name;
    private String description;
    private Double price;
    private Long reviewId;
}

