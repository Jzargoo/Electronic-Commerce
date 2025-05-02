package com.jzargo.services;

import com.jzargo.shared.model.CartDto;
import com.jzargo.shared.model.CartItemDto;

import java.util.List;


public interface CartService {

    List<CartDto> findAllByUserId(Long UserId);

    CartDto create(Long userId);

    CartDto add(CartItemDto dto);


    boolean delete (Long cartId, Integer productId);

    boolean clear(Long userId);
}
