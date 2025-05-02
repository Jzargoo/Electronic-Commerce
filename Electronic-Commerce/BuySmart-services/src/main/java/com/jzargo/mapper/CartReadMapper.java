package com.jzargo.mapper;

import com.jzargo.shared.model.CartDto;
import com.jzargo.entity.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartReadMapper implements Mapper<Cart, CartDto> {

    private final CartItemReadMapper cartItemReadMapper;

    public CartReadMapper(CartItemReadMapper cartItemReadMapper) {
        this.cartItemReadMapper = cartItemReadMapper;
    }

    @Override
    public CartDto map(Cart object) {
        return new CartDto(
                object.getId(),
                object.getBuyer().getId(),
                object.getName(),
                object.getDescription(),
                object.getItems().stream().map(cartItemReadMapper::map)
                        .toList()
        );
    }

}
