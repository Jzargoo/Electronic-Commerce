package com.jzargo.entity;

import com.jzargo.common.Role;
import com.jzargo.common.RoleListConverter;
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

    private String email;


    @Column(name = "created_on")
    @Builder.Default
    private LocalDate createdTime = LocalDate.now();

    @Builder.Default
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Product> OwnProducts = new ArrayList<>();

    @Convert(converter = RoleListConverter.class)
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    private String username;
    private String password;

    public List<Long> takeCartsId(){
        return carts.stream().map(Cart::getId).toList();
    }

    public List<Long> takeOrdersId() {
        return orders.stream().map(Order::getId).toList();
    }
}