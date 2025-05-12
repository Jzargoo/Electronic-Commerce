package com.jzargo.mapper;

import com.jzargo.entity.Payment;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.repository.OrderRepository;
import com.jzargo.shared.model.PaymentCreateAndUpdateDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.jzargo.shared.common.PaymentStatus.PENDING;

@Component
public class PaymentCreateAndUpdateMapper implements Mapper<PaymentCreateAndUpdateDto, Payment>{
    private final OrderRepository orderRepository;

    public PaymentCreateAndUpdateMapper(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @SneakyThrows
    @Override
    public Payment map(PaymentCreateAndUpdateDto object) {
        return Payment.builder()
                .amount(object.getAmount())
                .currency(object.getCurrency())
                .status(PENDING)
                .order(
                        orderRepository.findById(object.getOrderId())
                                .orElseThrow(() -> new DataNotFoundException("Order not found"))
                )
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .currency(object.getCurrency())
                .build();
    }
}
