package com.jzargo.mapper;

import com.jzargo.common.CartItemId;
import com.jzargo.entity.CartItem;
import com.jzargo.repository.CartRepository;
import com.jzargo.repository.ProductRepository;
import com.jzargo.shared.model.CartItemDto;
import org.springframework.stereotype.Component;

@Component
public class CartItemCreateMapper implements Mapper<CartItemDto,CartItem> {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    public CartItemCreateMapper(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItem map(CartItemDto object) {
        return CartItem.builder()
                .cart(cartRepository.findById(object.getCartId())
                        .orElseThrow())
                .quantity(object.getQuantity())
                .product(productRepository.findById(object.getProductId()).orElseThrow())
                .id(
                        new CartItemId(
                            object.getCartId(),
                            object.getProductId())
                )
                .build();
    }
}
