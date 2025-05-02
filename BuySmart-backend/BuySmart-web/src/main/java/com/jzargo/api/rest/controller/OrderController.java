package com.jzargo.api.rest.controller;

import com.jzargo.api.rest.checker.CheckUserId;
import com.jzargo.shared.model.OrderCreateAndUpdateDto;
import com.jzargo.shared.model.OrderReadDto;
import com.jzargo.dto.PageResponse;
import com.jzargo.services.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService1) {
        this.orderService = orderService1;
    }

    @CheckUserId
    @GetMapping("user/{userId}")
    public PageResponse<OrderReadDto> findAllByUserId(@PathVariable Long userId, Pageable pageable) {
        return PageResponse.of(
                orderService.findAllByUserId(userId, pageable)
        );
    }

    // Find an order by ID and userId
    @CheckUserId
    @GetMapping("{userId}/{id}")
    public OrderReadDto findById(@PathVariable Long userId, @PathVariable Long id) {
        return orderService.findById(id);
    }

    // Create a new order for the user
    @CheckUserId
    @PostMapping("/{userId}")
    public ResponseEntity<Void> create(@PathVariable Long userId, @RequestBody OrderCreateAndUpdateDto dto) {
        orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Delete an order by ID
    @CheckUserId
    @DeleteMapping("/{userId}/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long id) {
        boolean deleted = orderService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for deletion");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Helper method for redirecting to the user's orders page
    private static ResponseEntity<Void> redirectToUserOrder(Long userId) {
        String redirectUrl = "/api/orders/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }
}
