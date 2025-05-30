package com.jzargo.api.rest.controller;

import com.jzargo.services.OrderService;
import com.jzargo.shared.model.OrderCreateAndUpdateDto;
import com.jzargo.shared.model.OrderReadDto;
import com.jzargo.shared.model.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("user/{userId}")
    public PageResponse<OrderReadDto> findAllByUserId(@PathVariable Long userId, Pageable pageable) {
        Page<OrderReadDto> pages = orderService.findAllByUserId(userId, pageable);
        return PageResponse.of(
                pages.getSize(),pages.getTotalElements(),
                pages.getTotalPages(), pages.getContent()
        );
    }

    // Find an order by ID and userId
    @GetMapping("{userId}/{id}")
    public OrderReadDto findById( @PathVariable Long id) {
        return orderService.findById(id);
    }

    // Create a new order for the user
    @PostMapping("/{userId}")
    public ResponseEntity<Void> create(@RequestBody OrderCreateAndUpdateDto dto) {
        orderService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Delete an order by ID
    @DeleteMapping("/{userId}/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = orderService.delete(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found for deletion");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
