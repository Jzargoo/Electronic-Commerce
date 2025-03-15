package com.jzargo.repository;

import com.jzargo.entity.Cart;
import com.jzargo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAll();

    Optional<Cart> findById(Long id);

    Iterable<?> findCartByBuyer(User buyer);
}
