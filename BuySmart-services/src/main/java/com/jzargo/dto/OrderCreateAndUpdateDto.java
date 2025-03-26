package com.jzargo.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class OrderCreateAndUpdateDto {
    @Min(value = 1, message = "User id must greater than zero")
    private long userId;
    @Future(message = "Date of dispatch must be in the future")
    private LocalDate dateDispatch = LocalDate.now();
    @Min(value = 1, message = "Product ID must be greater than zero")
    private Integer productId;
    @NotNull(message = "Address cannot be null")
    private AddressDto address;
    @Min(value = 1, message = "Quantity must be greater than zero")
    private int quantity;
}
