package com.jzargo.entity;

import com.jzargo.common.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name="stripe_payment_id")
    private String StripePaymentId;
    @Column(name = "stripe_customer_id")
    private String StripeCustomerId;

    private Float amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String currency;

    @Column(columnDefinition = "jsonb")
    private String metadata;
}
