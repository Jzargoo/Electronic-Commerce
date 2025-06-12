package com.jzargo.shared.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "userId")
public class AddressDto {

    @NotEmpty(message = "Street cannot be empty")
    private String street;
    @NotEmpty(message = "City cannot be empty")
    private String city;
    @NotEmpty(message = "Country cannot be empty")
    private String country;
    @NotNull(message = "Post code cannot be empty")
    @Pattern(regexp = "^[0-9]{5,6}$", message = "Wrong format of post code")
    private String zipCode;

    private Long userId;
}

