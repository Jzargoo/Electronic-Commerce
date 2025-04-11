package com.jzargo.mapper;

import com.jzargo.dto.OrderReadDto;
import com.jzargo.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {
    private final AddressReadMapper addressReadMapper;

    public OrderReadMapper(AddressReadMapper addressReadMapper) {
        this.addressReadMapper = addressReadMapper;
    }

    public OrderReadDto map(Order order) {
        return new OrderReadDto(
                order.getId(),
                order.getBuyer().getId(),
                order.getProduct().getId(),
                order.getQuantity(),
                (order.getProduct().getPrice() * order.getQuantity()),
                order.getDateDispatch(),
                addressReadMapper.map(order.getAddress())
        );    }
}
