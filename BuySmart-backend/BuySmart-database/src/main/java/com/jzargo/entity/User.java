package com.jzargo.entity;

import com.jzargo.common.Role;
import com.jzargo.common.RoleListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Table(name = "users")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {
        "settings",
        "reviews",
        "coupons",
        "paymentMethods",
        "cart",
        "OwnProducts",
        "roles",
        "orders",
        "payments",
        "password"
})

@ToString(exclude = {
        "settings",
        "reviews",
        "coupons",
        "paymentMethods",
        "cart",
        "OwnProducts",
        "roles",
        "orders",
        "payments",
        "password"
})
public class User implements BaseEntity<Long>, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", unique=true)
    private String phone;

    @Column(name = "profile_image")
    private String ProfileImage;

    private String email;

    @OneToOne(mappedBy = "user")
    private UserSettings settings;

    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;

    @Column(name = "created_on")
    @Builder.Default
    private LocalDate createdTime = LocalDate.now();

    @OneToMany(
            mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @Builder.Default
    private List<Coupon> coupons = new ArrayList<>();

    @Builder.Default
    @OneToMany(
            mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY
    )
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @OneToOne(mappedBy = "buyer")
    private Cart cart;


    @Builder.Default
    @OneToMany(
            mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> OwnProducts = new ArrayList<>();

    @Convert(converter = RoleListConverter.class)
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    private String username;
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    public List<Integer> getOwnProductIds() {
        return OwnProducts.stream()
                .map(Product::getId)
                .toList();
    }
}