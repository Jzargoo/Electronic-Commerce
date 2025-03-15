package com.jzargo.services;

import com.jzargo.dto.ProductCreateAndUpdateDto;
import com.jzargo.dto.ProductReadDto;
import com.jzargo.entity.Product;
import com.jzargo.mapper.ProductCreateAndUpdateMapper;
import com.jzargo.mapper.ProductReadMapper;
import com.jzargo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductReadMapper productReadMapper;
    private final ProductCreateAndUpdateMapper productCreateAndUpdateMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductReadMapper productReadMapper,
                              ProductCreateAndUpdateMapper productCreateAndUpdateMapper) {
        this.productRepository = productRepository;
        this.productReadMapper = productReadMapper;
        this.productCreateAndUpdateMapper = productCreateAndUpdateMapper;
    }

    @Override
    public ProductReadDto findById(int id) {
        return productRepository.findById(id)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Override
    public List<ProductReadDto> findAll() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .toList();
    }

    @Override
    public ProductReadDto create(ProductCreateAndUpdateDto dto) {
        return Optional.ofNullable(productCreateAndUpdateMapper.map(dto))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Override
    public ProductReadDto update(int id, ProductCreateAndUpdateDto dto) {
        Product old = productRepository.findById(id).orElseThrow();
        return Optional.ofNullable(productCreateAndUpdateMapper.map(dto, old))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Override
    public boolean delete(int id) {
        Product old = productRepository.findById(id).orElseThrow();
        productRepository.delete(old);
        return productRepository.findById(id).isEmpty();
    }
}

