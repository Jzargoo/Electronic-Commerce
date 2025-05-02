package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_settings")
public class UserSettings {
    @Id
    private Long userId;

    private String language;
    private String currency;
    private String theme;
    private boolean notificationsEnabled;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
