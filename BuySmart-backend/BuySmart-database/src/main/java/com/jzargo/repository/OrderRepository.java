package com.jzargo.repository;

import com.jzargo.entity.Order;
import com.jzargo.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByBuyerId(Long userId, Pageable pageable);

    Optional<Order> findByPayment(Payment payment);
}
