package com.jzargo.services;

import com.jzargo.common.CartItemId;
import com.jzargo.entity.Cart;
import com.jzargo.entity.CartItem;
import com.jzargo.entity.Product;
import com.jzargo.mapper.CartItemReadMapper;
import com.jzargo.mapper.CartReadMapper;
import com.jzargo.repository.CartItemRepository;
import com.jzargo.repository.CartRepository;
import com.jzargo.repository.ProductRepository;
import com.jzargo.repository.UserRepository;
import com.jzargo.shared.model.CartDto;
import com.jzargo.shared.model.CartItemDto;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartReadMapper cartReadMapper;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartItemReadMapper cartItemReadMapper;

    public CartServiceImpl(CartRepository cartRepository, CartReadMapper cartReadMapper,
                           UserRepository userRepository,
                           CartItemRepository cartItemRepository1, ProductRepository productRepository1, CartItemReadMapper cartItemReadMapper) {
        this.cartRepository = cartRepository;
        this.cartReadMapper = cartReadMapper;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository1;
        this.productRepository = productRepository1;
        this.cartItemReadMapper = cartItemReadMapper;
    }

    @Override
    public List<CartDto> findAllByUserId(Long UserId) {
        return cartRepository.findAllByBuyerId(UserId).stream()
                .map(cartReadMapper::map).toList();
    }

    @SneakyThrows
    public Page<CartItemDto> findAllItemsByCartId(Long cartId, Pageable pageable) {
        return cartItemRepository.findAllByCartId(cartId, pageable)
                .map(cartItemReadMapper::map);
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

    @Override
    public boolean clear(Long cartId) {
        cartRepository.deleteById(cartId);
        return cartRepository.existsById(cartId);
    }
}
