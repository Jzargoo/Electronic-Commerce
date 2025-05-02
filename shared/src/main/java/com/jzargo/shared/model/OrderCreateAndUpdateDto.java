package com.jzargo.shared.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCreateAndUpdateDto {
    @Min(value = 1, message = "Product ID must be greater than zero")
    private Integer productId;
    @NotNull
    private Long userId;
    @NotNull(message = "Address cannot be null")
    private AddressDto address;
    @Min(value = 1, message = "Quantity must be greater than zero")
    private int quantity;
}
