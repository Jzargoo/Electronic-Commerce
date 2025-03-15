package com.jzargo.services;

import com.jzargo.dto.ProductCreateAndUpdateDto;
import com.jzargo.dto.ProductReadDto;

import java.util.List;

public interface ProductService {
    ProductReadDto findById(int id);
    List<ProductReadDto> findAll();
    ProductReadDto create(ProductCreateAndUpdateDto dto);
    ProductReadDto update(int id, ProductCreateAndUpdateDto dto);
    boolean delete(int id);
}
