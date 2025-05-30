package com.jzargo.api.rest.controller;


import com.jzargo.entity.User;
import com.jzargo.services.PaymentService;
import com.jzargo.services.PaymentServiceImpl;
import com.jzargo.services.UserService;
import com.jzargo.shared.model.PageResponse;
import com.jzargo.shared.model.PaymentReadDto;
import com.jzargo.shared.model.UserReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;

    public PaymentController(PaymentServiceImpl paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<PageResponse<PaymentReadDto>> getUserPurchases(@PathVariable Long userId, @ModelAttribute Pageable pageable){
        Page<PaymentReadDto> pages = paymentService.findAllByUserId(userId, pageable);
        return ResponseEntity.ok(
            PageResponse.of(
                pages.getSize(),pages.getTotalElements(),
                pages.getTotalPages(), pages.getContent())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentReadDto> getPurchase(@PathVariable Long id, @AuthenticationPrincipal User user) {
        PaymentReadDto  dto = paymentService.findById(id);
        UserReadDto userReadDto = userService.findById(
                dto.getUserId()
        ).orElseThrow();
        if(Objects.equals(userReadDto.getId(), user.getId())){
            throw new ResponseStatusException(FORBIDDEN);
        }
        return ResponseEntity.ok(
                paymentService.findById(id)
        );
    }

//    @PostMapping
//    public ResponseEntity<PaymentReadDto> createPurchase(@RequestBody PaymentCreateAndUpdateDto request) {
//    }
//
}
