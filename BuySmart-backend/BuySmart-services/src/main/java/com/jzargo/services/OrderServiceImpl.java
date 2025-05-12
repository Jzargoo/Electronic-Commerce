package com.jzargo.services;

import com.jzargo.entity.Order;
import com.jzargo.entity.Product;
import com.jzargo.events.OrderCreatedEvent;
import com.jzargo.mapper.OrderCreateMapper;
import com.jzargo.mapper.OrderReadMapper;
import com.jzargo.repository.OrderRepository;
import com.jzargo.repository.ProductRepository;
import com.jzargo.shared.model.OrderCreateAndUpdateDto;
import com.jzargo.shared.model.OrderReadDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateMapper orderCreateMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderReadMapper orderReadMapper,
                            OrderCreateMapper orderCreateMapper,
                            ApplicationEventPublisher applicationEventPublisher,
                            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderReadMapper = orderReadMapper;
        this.orderCreateMapper = orderCreateMapper;
        this.applicationEventPublisher = applicationEventPublisher;
        this.productRepository = productRepository;
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
        Product product = productRepository.findById(orderCreateDto.getProductId()).orElseThrow();
        Order map = orderCreateMapper.map(orderCreateDto);
        map = orderRepository.saveAndFlush(map);
        applicationEventPublisher.publishEvent(
                new OrderCreatedEvent(
                        this,
                        orderCreateDto.getUserId(),
                        map.getId(),
                        product.getPrice() * orderCreateDto.getQuantity()
                )
        );
        return orderReadMapper.map(map);
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
