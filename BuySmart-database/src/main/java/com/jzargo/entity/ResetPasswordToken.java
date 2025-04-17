package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reset_password_tokens")
public class ResetPasswordToken {

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expires_at")
    LocalDateTime expiryDate;
    String token;
}
