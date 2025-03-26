package com.jzargo.entity;

import com.jzargo.common.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString(exclude = "buyer")
@EqualsAndHashCode(exclude = "buyer")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name = "date_dispatch")
    private LocalDate DateDispatch;

    @ManyToOne
    @JoinColumn(name="buyer_id")
    private User buyer;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer quantity;
}
