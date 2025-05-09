package com.jzargo.repository;

import com.jzargo.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

    List<Cart> findAllByBuyerId(Long id);
}
