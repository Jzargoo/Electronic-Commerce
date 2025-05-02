package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupons")
public class Coupon extends Discount {
    private String code;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToMany
    @JoinTable(
            name = "users",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user;
}
