package com.jzargo.mapper;

import com.jzargo.dto.CartItemDto;
import com.jzargo.entity.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemReadMapper implements Mapper<CartItem, CartItemDto> {

    @Override
    public CartItemDto map(CartItem object) {
        return new CartItemDto(
                object.getId().getCartId(),
                object.getId().getProductId(),
                object.getQuantity()
        );
    }

}
