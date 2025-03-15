package com.jzargo.services;

import com.jzargo.dto.CartDto;

import java.util.List;

public interface CartService {
    CartDto findById(Long id);
    List<CartDto> findAll();
    CartDto create(CartDto dto);
    CartDto update(Long id, CartDto dto);
    boolean delete(Long id);
}
