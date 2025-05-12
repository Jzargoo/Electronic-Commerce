package com.jzargo.shared.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ProductReadDto {
    private Integer id;
    private String image;
    private String name;
    private Double price;
}

