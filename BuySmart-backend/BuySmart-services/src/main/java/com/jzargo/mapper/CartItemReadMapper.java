package com.jzargo.mapper;

import com.jzargo.entity.CartItem;
import com.jzargo.shared.model.CartItemDto;
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
