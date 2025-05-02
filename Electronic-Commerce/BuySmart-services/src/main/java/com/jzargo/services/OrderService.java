package com.jzargo.services;

import com.jzargo.shared.model.OrderCreateAndUpdateDto;
import com.jzargo.shared.model.OrderReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    Page<OrderReadDto> findAllByUserId(Long userId, Pageable pageable);
    OrderReadDto findById(Long id);
    OrderReadDto create(OrderCreateAndUpdateDto orderCreateDto);
    boolean delete(Long id);

}
