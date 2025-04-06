package com.jzargo.services;

import com.jzargo.dto.OrderCreateAndUpdateDto;
import com.jzargo.dto.OrderReadDto;
import com.jzargo.mapper.OrderCreateMapper;
import com.jzargo.mapper.OrderReadMapper;
import com.jzargo.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateMapper orderCreateMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderReadMapper orderReadMapper, OrderCreateMapper orderCreateMapper) {
        this.orderRepository = orderRepository;
        this.orderReadMapper = orderReadMapper;
        this.orderCreateMapper = orderCreateMapper;
    }

    @Override
    public Page<OrderReadDto> findAllByUserId(Long userId, Pageable pageable) {
        return orderRepository
                .findAllByBuyerId(userId, pageable).map(orderReadMapper::map);
    }

    @Override
    public OrderReadDto findById(Long id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Override
    public OrderReadDto create(OrderCreateAndUpdateDto orderCreateDto) {
        return Optional.ofNullable(orderCreateDto)
                .map(orderCreateMapper::map)
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map)
                .orElseThrow();
    }
    @Override
    public boolean delete(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.delete(order);
                    return true;
                })
                .orElse(false);
    }
}
