package com.jzargo.services;

import com.jzargo.common.CartItemId;
import com.jzargo.dto.CartDto;
import com.jzargo.dto.CartItemDto;
import com.jzargo.entity.Cart;
import com.jzargo.entity.CartItem;
import com.jzargo.entity.Product;
import com.jzargo.mapper.CartCreateMapper;
import com.jzargo.mapper.CartReadMapper;
import com.jzargo.repository.CartItemRepository;
import com.jzargo.repository.CartRepository;
import com.jzargo.repository.ProductRepository;
import com.jzargo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartReadMapper cartReadMapper;
    private final UserRepository userRepository;
    private final CartCreateMapper cartCreateMapper;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartReadMapper cartReadMapper,
                           UserRepository userRepository, CartCreateMapper cartCreateMapper,
                           CartItemRepository cartItemRepository1, ProductRepository productRepository, ProductRepository productRepository1) {
        this.cartRepository = cartRepository;
        this.cartReadMapper = cartReadMapper;
        this.userRepository = userRepository;
        this.cartCreateMapper = cartCreateMapper;
        this.cartItemRepository = cartItemRepository1;
        this.productRepository = productRepository1;
    }

    @Override
    public CartDto findByUserId(Long UserId) {
        return cartRepository.findByBuyerId(UserId)
                .map(cartReadMapper::map)
                .orElse(
                        create(UserId));
    }



    @Override
    public CartDto create(Long userId) {
        return Optional.of(cartRepository.saveAndFlush(
                Cart.builder()
                        .buyer(
                                userRepository.findById(userId).orElseThrow()
                        )
                        .build())
                )
                .map(cartReadMapper::map).orElseThrow();
    }

    @Override
    public CartDto add(CartItemDto cartItemDto) {

        Cart cart = cartRepository.findById(cartItemDto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow();

        CartItemId id = new CartItemId(cartItemDto.getCartId(), cartItemDto.getProductId());

        cartItemRepository.findById(id)
                .ifPresentOrElse(
                        cartItem -> cartItem.increaseQuantity(cartItemDto.getQuantity()),
                        ()-> cart.addCartItem(new CartItem(id,cart,product,cartItemDto.getQuantity()))
                );

        cartRepository.saveAndFlush(cart);
        return cartReadMapper.map(cart);
    }


    @Override
    public boolean delete(Long cartId, Integer productId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow();
        CartItemId id = new CartItemId(cartId, productId);

        cartItemRepository.findById(id)
                .ifPresent(cartItem ->
                        cart.getItems().remove(cartItem));

        return cartItemRepository.existsById(id);
    }
}
