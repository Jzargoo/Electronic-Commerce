package com.jzargo.services;

import com.jzargo.dto.CartItemDto;

import java.util.List;

public interface CartItemService {
    CartItemDto findById(Long cartId, int productId);
    List<CartItemDto> findAll();
    CartItemDto create(CartItemDto dto);
    CartItemDto update(Long cartId, int productId, CartItemDto dto);
    boolean delete(Long cartId, int productId);
}
