package com.jzargo.services;

import com.jzargo.dto.CartDto;
import com.jzargo.mapper.CartCreateMapper;
import com.jzargo.mapper.CartReadMapper;
import com.jzargo.repository.CartRepository;
import com.jzargo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartReadMapper cartReadMapper;
    private final CartCreateMapper cartCreateMapper;

    public CartServiceImpl(CartRepository cartRepository, CartReadMapper cartReadMapper,
                           CartCreateMapper cartCreateMapper) {
        this.cartRepository = cartRepository;
        this.cartReadMapper = cartReadMapper;
        this.cartCreateMapper = cartCreateMapper;
    }

    @Override
    public CartDto findById(Long id) {
        return cartRepository.findById(id)
                .map(cartReadMapper::map)
                .orElseThrow();
    }

    @Override
    public List<CartDto> findAll() {
        return cartRepository.findAll().stream()
                .map(cartReadMapper::map).toList();
    }

    @Override
    public CartDto create(CartDto dto) {
        return Optional.ofNullable(cartCreateMapper.map(dto))
                .map(cartRepository::saveAndFlush)
                .map(cartReadMapper::map).orElseThrow();
    }

    @Override
    public CartDto update(Long id, CartDto dto) {

        var cart = cartRepository.findById(id)
                .orElseThrow();

        return Optional.ofNullable(cartCreateMapper.map(dto, cart))
                .map(cartRepository::saveAndFlush)
                .map(cartReadMapper::map).orElseThrow();
    }

    @Override
    public boolean delete(Long id) {
        var cart=cartRepository.findById(id).orElseThrow();
        cartRepository.delete(cart);
        return cartRepository.existsById(id);
    }
}
