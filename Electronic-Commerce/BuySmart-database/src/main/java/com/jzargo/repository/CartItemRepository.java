package com.jzargo.repository;

import com.jzargo.common.CartItemId;
import com.jzargo.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    Page<CartItem> findAllByCartId(Long cartId, Pageable pageable);
}
