package com.jzargo.services;

import com.jzargo.shared.model.CartDto;
import com.jzargo.shared.model.CartItemDto;


public interface CartService {

    CartDto findByUserId(Long UserId);

    CartDto create(Long userId);

    CartDto add(CartItemDto dto);


    boolean delete (Long cartId, Integer productId);


    boolean clear(Long userId);
}
