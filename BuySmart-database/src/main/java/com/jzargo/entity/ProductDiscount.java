package com.jzargo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_discounts")
@EqualsAndHashCode(callSuper = true)
public class ProductDiscount extends Discount{
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
