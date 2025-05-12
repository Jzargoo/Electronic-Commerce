package com.jzargo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_discounts")
@EqualsAndHashCode(callSuper = true)
public class ProductDiscount extends Discount{
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
