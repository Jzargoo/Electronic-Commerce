package com.jzargo.repository;

import com.jzargo.entity.Cart;
import com.jzargo.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Relation> {
}
