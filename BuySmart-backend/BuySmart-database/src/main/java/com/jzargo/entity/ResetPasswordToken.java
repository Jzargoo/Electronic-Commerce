package com.jzargo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
