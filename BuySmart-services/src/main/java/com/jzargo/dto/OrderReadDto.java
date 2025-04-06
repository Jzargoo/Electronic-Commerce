package com.jzargo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OrderReadDto {
    private Long id;
    private Long buyerId;
    private Integer productId;
    private Integer quantity;
    private Double totalPrice;
    private LocalDate dateDispatch;
    private AddressDto addressDto;
}
