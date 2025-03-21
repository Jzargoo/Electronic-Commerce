package com.jzargo.repository;

import com.jzargo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAll();

    Optional<Cart> findByBuyerId(Long id);

}
