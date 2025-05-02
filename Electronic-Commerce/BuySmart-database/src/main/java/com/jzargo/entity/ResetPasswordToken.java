package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table(name = "reset_password_tokens")
@Entity
public class ResetPasswordToken {

    @Id
    private Long userId;

    @Column(name = "expires_at")
    LocalDateTime expiryDate;
    String token;
}
