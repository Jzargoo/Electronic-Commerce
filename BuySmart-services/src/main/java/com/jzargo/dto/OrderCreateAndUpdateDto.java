package com.jzargo.dto;

import com.jzargo.entity.Address;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class OrderCreateAndUpdateDto {
    @Min(value = 1, message = "User id must greater than zero")
    private long userId;
    @Future(message = "Date of dispatch must be in the future")
    private LocalDate dateDispatch;
    @Min(value = 1, message = "Product ID must be greater than zero")
    private Integer productId;
    @NotNull(message = "Address cannot be null")
    private Address address;
}
