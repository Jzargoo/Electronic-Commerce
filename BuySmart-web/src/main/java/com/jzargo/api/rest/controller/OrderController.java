package com.jzargo.api.rest.controller;

import com.jzargo.api.rest.checker.CheckUserId;
import com.jzargo.dto.OrderCreateAndUpdateDto;
import com.jzargo.dto.OrderReadDto;
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

    @CheckUserId
    @GetMapping("{userId}/{id}")
    public OrderReadDto findById(@PathVariable Long id, @PathVariable String userId) {
        return orderService.findById(id);
    }

    @CheckUserId
    @PostMapping("/{userId}")
    public ResponseEntity<Void> create(@PathVariable Long userId, OrderCreateAndUpdateDto dto){
        orderService.create(dto);
        return redirectToUserOrder(userId);
    }
    @CheckUserId
    @DeleteMapping("/{userId}/{id}")
    public void delete(@PathVariable Long id, @PathVariable Long userId){
        boolean delete = orderService.delete(id);
        if (!delete) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    private static ResponseEntity<Void> redirectToUserOrder(Long UserId) {
        String redirectUrl = "/api/products/users/" + UserId;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        // HTTP 302 is for a temporary redirect
        return ResponseEntity.status(302)
                .headers(headers)
                .build();
    }
}
