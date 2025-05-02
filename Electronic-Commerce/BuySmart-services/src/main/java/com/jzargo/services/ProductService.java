package com.jzargo.services;

import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.filtration.ProductFilter;
import com.jzargo.shared.model.ProductCreateAndUpdateDto;
import com.jzargo.shared.model.ProductReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductReadDto findById(int id);
    Page<ProductReadDto> findAll(ProductFilter productFilter, Pageable pageable);
    ProductReadDto create(ProductCreateAndUpdateDto dto);
    ProductReadDto update(int id, ProductCreateAndUpdateDto dto);
    boolean delete(int id);
    List<byte[]> loadImages(int id) throws DataNotFoundException, DataNotFoundException;
    Page<ProductReadDto> findAllByUserId(Long userId, Pageable pageable);
}
