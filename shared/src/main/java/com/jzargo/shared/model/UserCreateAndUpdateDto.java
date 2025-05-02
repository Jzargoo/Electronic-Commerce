package com.jzargo.shared.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateAndUpdateDto {
    private byte[] ProfileImage;

    @Pattern(regexp = "^(\\+?\\d{1,4}?[\\s-]?\\(?\\d{1,3}\\)?[\\s-]?\\d{1,4}[\\s-]?\\d{1,4}[\\s-]?\\d{1,4})$",
            message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Username is required and cannot be empty")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have at least 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Password must contain at least one number, one uppercase letter, one lowercase letter, and one special character")
    private String password;
    @Email(message = "Invalid email format")
    private String Email;
    private String role;
}
