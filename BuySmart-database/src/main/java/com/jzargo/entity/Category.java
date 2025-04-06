package com.jzargo.entity;

import com.jzargo.common.Categories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private Categories category;

    @OneToMany
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<Coupon> coupons= new ArrayList<>();

    @OneToOne(mappedBy = "category")
    private SeasonDiscount seasonDiscounts;
}

