package com.jzargo.mapper;

import com.jzargo.dto.OrderCreateAndUpdateDto;
import com.jzargo.entity.Order;
import com.jzargo.repository.ProductRepository;
import com.jzargo.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderCreateMapper implements Mapper<OrderCreateAndUpdateDto, Order> {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AddressCreateMapper addressCreateMapper;

    public OrderCreateMapper(ProductRepository productRepository, UserRepository userRepository, AddressCreateMapper addressCreateMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.addressCreateMapper = addressCreateMapper;
    }

    @Override
    public Order map(OrderCreateAndUpdateDto orderCreateDto) {
        return Order.builder()
                .DateDispatch(LocalDate.now())
                .product(
                        productRepository.findById(orderCreateDto.getProductId())
                                .orElseThrow()
                )
                .buyer(
                        userRepository.findById(orderCreateDto.getUserId())
                                .orElseThrow()
                )
                .address(
                        addressCreateMapper.map(orderCreateDto.getAddress())
                )
                .quantity(orderCreateDto.getQuantity())
                .build();
    }

}
