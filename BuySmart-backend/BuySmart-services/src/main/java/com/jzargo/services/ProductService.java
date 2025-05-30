package com.jzargo.services;

import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.shared.filters.ProductFilter;
import com.jzargo.shared.model.ProductCreateAndUpdateDto;
import com.jzargo.shared.model.ProductDetails;
import com.jzargo.shared.model.ProductReadDto;
import com.stripe.exception.PermissionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDetails findById(int id);
    Page<ProductReadDto> findAll(ProductFilter productFilter, Pageable pageable);
    ProductDetails create(ProductCreateAndUpdateDto dto);
    ProductDetails update(int id, ProductCreateAndUpdateDto dto) throws PermissionException;

    boolean delete(int id, Long userId);

    List<byte[]> loadImages(int id) throws DataNotFoundException;
    byte[] loadImage(String name) throws  IOException;
    Page<ProductReadDto> findAllByUserId(Long userId, Pageable pageable);

    List<ProductReadDto> findRandom();


}
