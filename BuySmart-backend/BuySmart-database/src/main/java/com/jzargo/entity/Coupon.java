package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
            name = "user_activated_coupons",
            joinColumns = @JoinColumn(name = "coupon_id"))
    private List<User> user;
}
