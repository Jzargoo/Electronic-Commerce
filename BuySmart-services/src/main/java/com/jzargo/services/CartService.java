package com.jzargo.services;

import com.jzargo.dto.CartDto;
import com.jzargo.dto.CartItemDto;


public interface CartService {

    CartDto findByUserId(Long UserId);

    CartDto create(Long userId);

    CartDto add(CartItemDto dto);


    boolean delete (Long cartId, Integer productId);
}
