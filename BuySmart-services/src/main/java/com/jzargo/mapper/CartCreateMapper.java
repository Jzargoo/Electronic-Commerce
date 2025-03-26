package com.jzargo.mapper;

import com.jzargo.dto.CartDto;
import com.jzargo.entity.Cart;
import com.jzargo.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CartCreateMapper implements Mapper<CartDto, Cart> {
    private final CartItemCreateMapper cartItemCreateMapper;
    private final UserRepository userRepository;

    public CartCreateMapper(CartItemCreateMapper cartItemCreateMapper, UserRepository userRepository1) {
        this.cartItemCreateMapper = cartItemCreateMapper;
        this.userRepository = userRepository1;
    }

    @Override
    public Cart map(CartDto object) {
        return Cart.builder()
                .name(object.getName())
                .description(object.getDescription())
                .items(object.getItems().stream()
                        .map(cartItemCreateMapper::map).toList())
                .buyer(userRepository.findById(
                        object.getBuyerId()).orElseThrow())
                .build();
    }
}
