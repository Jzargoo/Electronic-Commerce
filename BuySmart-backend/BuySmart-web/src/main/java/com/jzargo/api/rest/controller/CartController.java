package com.jzargo.api.rest.controller;

import com.jzargo.api.rest.checker.CheckUserId;
import com.jzargo.shared.model.CartDto;
import com.jzargo.shared.model.CartItemDto;
import com.jzargo.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get all cart items for a user by userId
    @CheckUserId
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartDto>> findByAllId(@PathVariable Long userId) {
        List<CartDto> cartItems = cartService.findAllByUserId(userId);
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // No items found for this user
        }
        return ResponseEntity.ok(cartItems);
    }

    // Add an item to the cart for a user
    @CheckUserId
    @PutMapping("/{userId}")
    public ResponseEntity<CartDto> add(@RequestBody @Validated CartItemDto cartItemDto, @PathVariable Long userId) {
        CartDto updatedCartItem = cartService.add(cartItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItem);
    }

    // Delete a specific cart item for a user
    @CheckUserId
    @DeleteMapping("/{productId}/{cartItemId}")
    public ResponseEntity<Void> delete(@PathVariable Integer productId, @PathVariable Long cartItemId) {
        boolean deleted = cartService.delete(cartItemId, productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Clear all items from the user's cart
    @CheckUserId
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clear(@PathVariable Long userId) {
        boolean cleared = cartService.clear(userId);
        if (cleared) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}