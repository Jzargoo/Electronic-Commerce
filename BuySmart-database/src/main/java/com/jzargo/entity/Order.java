package com.jzargo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "buyer")
@EqualsAndHashCode(exclude = "buyer")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name = "date_dispatch")
    private LocalDate DateDispatch;

    @ManyToOne
    @JoinColumn(name="buyer_id")
    private User buyer;

}
