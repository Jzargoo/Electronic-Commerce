package com.jzargo.api.rest.controller;

import com.jzargo.api.rest.checker.CheckUserId;
import com.jzargo.dto.CartDto;
import com.jzargo.dto.CartItemDto;
import com.jzargo.services.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @CheckUserId
    @GetMapping("/{userId}")
    public List<CartDto> findByAllId(@PathVariable Long userId){
        return cartService.findAllByUserId(userId);
    }

    @CheckUserId
    @PutMapping("/{userId}")
    public CartDto add(@RequestBody CartItemDto cartItemDto, @PathVariable String userId){
        return cartService.add(cartItemDto);
    }
    @CheckUserId
    @DeleteMapping("/{productId}/{cartItemId}")
    public boolean delete(@PathVariable Integer productId, @PathVariable Long cartItemId) {
        return cartService.delete(cartItemId, productId);
    }
    @CheckUserId
    @DeleteMapping("/{userId}")
    public boolean clear(@PathVariable Long userId){
        return cartService.clear(userId);
    }
}
