package com.jzargo.events;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;


public class OrderCreatedEvent extends ApplicationEvent{
    private final Long userId;
    private final Long orderId;
    private final Double amount;

    public Long getUserId() {
        return userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public OrderCreatedEvent(Object source, Long userId, Long orderId, Double amount) {
        super(source);
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }

}
