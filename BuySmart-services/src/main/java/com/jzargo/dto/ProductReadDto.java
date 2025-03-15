package com.jzargo.dto;

import com.jzargo.common.Tags;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReadDto {
    private Integer id;
    private LocalDateTime created;
    private List<Tags> tags;
    private List<String> images;
    private String name;
    private String description;
    private Float price;
}

