package com.jzargo.shared.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LoginCreateDto {
    @NotEmpty(message = "User's name cannot be a null or empty")
    private String username;
    @NotEmpty(message = "User's password cannot be a null or empty")
    private String password;
}
