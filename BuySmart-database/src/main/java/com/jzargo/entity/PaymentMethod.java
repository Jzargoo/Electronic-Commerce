package com.jzargo.entity;

import com.jzargo.common.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethod implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name="stripe_id")
    String stripeId;

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private String createdAt;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(name = "last4")
    private String last4;
    @Column(name = "brand")
    private String brand;
}
