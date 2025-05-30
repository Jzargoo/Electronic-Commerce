package com.jzargo.api.rest.controller;

import com.jzargo.services.CartService;
import com.jzargo.shared.model.CartDto;
import com.jzargo.shared.model.CartItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get all cart items for a user by userId
    @GetMapping("/view")
    public ResponseEntity<CartDto> findByAllId(Authentication authentication) {
        String id = ((Jwt) authentication.getPrincipal()).getSubject();
        CartDto cart = cartService.findByUserId(
                Long.valueOf(id)
        );
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // No items found for this user
        }
        return ResponseEntity.ok(cart);
    }

    // Add an item to the cart for a user
    @PutMapping("edit")
    public ResponseEntity<CartDto> add(@RequestBody @Validated CartItemDto cartItemDto){
        CartDto updatedCartItem = cartService.add(cartItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItem);
    }

    // Delete a specific cart item for a user
    @DeleteMapping("/{productId}/{cartItemId}")
    public ResponseEntity<Void> delete(@PathVariable Integer productId, @PathVariable Long cartItemId) {
        boolean deleted = cartService.delete(cartItemId, productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Clear all items from the user's cart
    @DeleteMapping()
    public ResponseEntity<Void> clear(Authentication auth) {
        String  userId= ((Jwt) auth.getPrincipal()).getSubject();
        boolean cleared = cartService.clear(
                Long.valueOf(userId));
        if (cleared) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}