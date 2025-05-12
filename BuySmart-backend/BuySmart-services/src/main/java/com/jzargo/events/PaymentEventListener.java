package com.jzargo.events;

import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.repository.UserSettingsRepository;
import com.jzargo.services.PaymentService;
import com.jzargo.shared.model.PaymentCreateAndUpdateDto;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventListener {

    private final PaymentService paymentService;
    private final UserSettingsRepository userSettingsRepository;

    public PaymentEventListener(PaymentService paymentService, UserSettingsRepository userSettingsRepository) {
        this.paymentService = paymentService;
        this.userSettingsRepository = userSettingsRepository;
    }

    @SneakyThrows
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        paymentService.createCheckoutSession(
                event.getUserId(),
                PaymentCreateAndUpdateDto.builder()
                        .orderId(event.getOrderId())
                        .amount(event.getAmount())
                        .currency(
                                userSettingsRepository.findByUserId(
                                event.getUserId()
                                ).orElseThrow(() -> new DataNotFoundException("User settings not found"))
                                        .getCurrency()
                        )
                .build());
    }
}
