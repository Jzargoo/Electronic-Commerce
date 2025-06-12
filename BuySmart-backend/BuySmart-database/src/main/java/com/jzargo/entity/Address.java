package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "address")
public class Address implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String country;
    @Column(name = "zip_code")
    private String zipCode;
    @ManyToMany(mappedBy = "addresses")
    private List<UserSettings> userSettings = new ArrayList<>();

    public void addUserSettings(UserSettings userSettings) {
        this.userSettings.add(userSettings);
        userSettings.getAddresses().add(this);
    }
}
