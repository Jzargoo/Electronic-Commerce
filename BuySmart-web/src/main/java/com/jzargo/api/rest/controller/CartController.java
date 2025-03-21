package com.jzargo.api.rest.controller;

import com.jzargo.dto.CartDto;
import com.jzargo.dto.CartItemDto;
import com.jzargo.services.CartService;
import com.jzargo.services.UserService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public CartDto findById(@PathVariable Long id){
        return cartService.findByUserId(id);
    }

    @PostMapping("/{id}")
    public CartDto save(@PathVariable Long id){
        userService.findById(id).orElseThrow();
        return cartService.findByUserId(id);
    }

    @PutMapping
    public CartDto add(@RequestBody CartItemDto cartItemDto){
        return cartService.add(cartItemDto);
    }

    @DeleteMapping("/{productId}/{cartItemId}")
    public boolean delete(@PathVariable Integer productId, @PathVariable Long cartItemId) {
        return cartService.delete(cartItemId, productId);
    }
}
