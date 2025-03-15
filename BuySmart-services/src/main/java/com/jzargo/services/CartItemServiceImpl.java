package com.jzargo.services;

import com.jzargo.dto.CartItemDto;
import com.jzargo.entity.Cart;
import com.jzargo.entity.CartItem;
import com.jzargo.common.CartItemId;
import com.jzargo.entity.Product;
import com.jzargo.mapper.CartItemCreateMapper;
import com.jzargo.mapper.CartItemReadMapper;
import com.jzargo.repository.CartItemRepository;
import com.jzargo.repository.CartRepository;
import com.jzargo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemReadMapper cartItemReadMapper;
    private final CartItemCreateMapper cartItemCreateAndUpdateMapper;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository,
                               CartItemReadMapper cartItemReadMapper,
                               CartItemCreateMapper cartItemCreateMapper,
                               CartRepository cartRepository,
                               ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemReadMapper = cartItemReadMapper;
        this.cartItemCreateAndUpdateMapper = cartItemCreateMapper;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItemDto findById(Long cartId, int productId) {
        return cartItemRepository.findById(new CartItemId(cartId, productId))
                .map(cartItemReadMapper::map)
                .orElseThrow();
    }

    @Override
    public List<CartItemDto> findAll() {
        return cartItemRepository.findAll().stream()
                .map(cartItemReadMapper::map)
                .toList();
    }

    @Override
    public CartItemDto create(CartItemDto dto) {
        Cart cart = cartRepository.findById(dto.getCartId()).orElseThrow();
        Product product = productRepository.findById(dto.getProductId()).orElseThrow();

        CartItem cartItem = cartItemCreateAndUpdateMapper.map(dto);
        cartItem.setCart(cart);
        cartItem.setProduct(product);

        return Optional.of(cartItemRepository.saveAndFlush(cartItem))
                .map(cartItemReadMapper::map)
                .orElseThrow();
    }

    @Override
    public CartItemDto update(Long cartId, int productId, CartItemDto dto) {
        CartItem cartItem = cartItemRepository.findById(new CartItemId(cartId, productId))
                .orElseThrow();

        return Optional.of(cartItemCreateAndUpdateMapper.map(dto, cartItem))
                .map(cartItemRepository::saveAndFlush)
                .map(cartItemReadMapper::map)
                .orElseThrow();
    }

    @Override
    public boolean delete(Long cartId, int productId) {
        CartItem cartItem = cartItemRepository.findById(new CartItemId(cartId, productId))
                .orElseThrow();
        cartItemRepository.delete(cartItem);
        return cartItemRepository.existsById(new CartItemId(cartId, productId));
    }
}
