package com.jzargo.api.rest.controller;

import com.jzargo.api.rest.checker.CheckUserId;
import com.jzargo.dto.PageResponse;
import com.jzargo.dto.PaymentReadDto;
import com.jzargo.dto.UserReadDto;
import com.jzargo.entity.User;
import com.jzargo.services.PaymentService;
import com.jzargo.services.PaymentServiceImpl;
import com.jzargo.services.UserService;
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

    @CheckUserId
    @GetMapping("/user/{userId}")
    ResponseEntity<PageResponse<PaymentReadDto>> getUserPurchases(@PathVariable Long userId, @ModelAttribute Pageable pageable){
        return ResponseEntity.ok(
            PageResponse.of(
                    paymentService.findAllByUserId(userId,pageable)
            )
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
    //@PostMapping("/webhook")
    //p
}
