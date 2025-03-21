package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "users")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", unique=true)
    private String phone;

    @Column(name = "profile_image")
    private String ProfileImage;


    @Column(name = "created_on")
    @Builder.Default
    private LocalDate createdTime = LocalDate.now();

    @OneToOne(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Product> OwnProducts = new ArrayList<>();


    @OneToMany(mappedBy = "buyer")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    private String username;
    private String password;

    public void setCart(Cart cart){
        this.cart=cart;
        cart.setBuyer(this);
    }
}
