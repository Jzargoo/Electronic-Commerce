package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "user_settings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;
    private String currency;
    private String theme;
    private boolean notificationsEnabled;
    @ManyToMany
    @Builder.Default
    @JoinTable(
            name = "address_to_users",
            joinColumns = @JoinColumn(name = "settings_id"),
            inverseJoinColumns = @JoinColumn(name = "address")
    )
    private List<Address> addresses = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
