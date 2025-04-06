package com.jzargo.services;

import com.jzargo.dto.CartDto;
import com.jzargo.dto.CartItemDto;

import java.util.List;


public interface CartService {

    List<CartDto> findAllByUserId(Long UserId);

    CartDto create(Long userId);

    CartDto add(CartItemDto dto);


    boolean delete (Long cartId, Integer productId);

    boolean clear(Long userId);
}
