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
    private final ImageStorageService imageStorageService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductReadMapper productReadMapper,
                              ProductCreateAndUpdateMapper productCreateAndUpdateMapper, ImageStorageService imageStorageService) {
        this.productRepository = productRepository;
        this.productReadMapper = productReadMapper;
        this.productCreateAndUpdateMapper = productCreateAndUpdateMapper;
        this.imageStorageService = imageStorageService;
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
        Product product = productCreateAndUpdateMapper.map(dto);
        if (product == null) {
            throw new IllegalStateException("Mapping resulted in null product");
        }

        List<String> savedImages = imageStorageService.storeProductFiles(dto.getImages());
        product.setImages(savedImages);

        Product savedProduct = productRepository.saveAndFlush(product);

        return productReadMapper.map(savedProduct);

    }

    @Override
    public ProductReadDto update(int id, ProductCreateAndUpdateDto dto) {
        Product old = productRepository.findById(id).orElseThrow();

        return Optional.ofNullable(productCreateAndUpdateMapper.map(dto, old))
                .map(product -> {
                    imageStorageService.deleteProductFiles(product.getImages());

                    List<String> savedImages = imageStorageService.storeProductFiles(dto.getImages());
                    product.setImages(savedImages);
                    return productRepository.saveAndFlush(product);
                })
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Override
    public boolean delete(int id) {
        Product old = productRepository.findById(id).orElseThrow();
        productRepository.delete(old);
        imageStorageService.deleteProductFiles(old.getImages());
        return productRepository.findById(id).isEmpty();
    }
}

