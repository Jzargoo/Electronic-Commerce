package com.jzargo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class UserReadDto {
    private Long id;
    private String ProfileImage;
    private String phone;
    private LocalDate createdTime;
    private List<Long> cartsId;
    private List<Long> ordersId;
    private List<ProductReadDto> OwnProducts;
    private String username;
}
