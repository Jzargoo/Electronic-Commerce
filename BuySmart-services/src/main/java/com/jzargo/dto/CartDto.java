package com.jzargo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;
    @NotNull(message = "Buyer ID cannot be null")
    private  Long buyerId;
    private String name;
    private String description;
    private List<CartItemDto> items;
}
