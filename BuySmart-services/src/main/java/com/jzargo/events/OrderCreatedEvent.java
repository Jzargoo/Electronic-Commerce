package com.jzargo.events;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
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
