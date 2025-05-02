package com.jzargo.shared.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {
    @NotNull(message = "Cart ID cannot be null")
    private Long cartId;

    @Min(value = 1, message = "Product ID must be greater than zero")
    private int productId;

    @Min(value = 1, message = "Quantity must be greater than zero")
    private int quantity;

}
