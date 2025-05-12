package com.jzargo.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class OrderCreatedEvent extends ApplicationEvent{
    private final Long userId;
    private final Long orderId;
    private final Double amount;

    public OrderCreatedEvent(Object source, Long userId, Long orderId, Double amount) {
        super(source);
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }

}
