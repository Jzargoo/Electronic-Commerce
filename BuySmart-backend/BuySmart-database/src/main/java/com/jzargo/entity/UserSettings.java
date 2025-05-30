package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    @ManyToMany
    @JoinTable(
            name = "address_to_users",
            joinColumns = @JoinColumn(name = "settings_id"),
            inverseJoinColumns = @JoinColumn(name = "address")
    )
    private List<Address> addresses = new ArrayList<>();
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
