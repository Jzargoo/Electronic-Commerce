package com.jzargo.services;

import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.filtration.ProductFilter;
import com.jzargo.common.QPredicate;
import com.jzargo.shared.model.ProductCreateAndUpdateDto;
import com.jzargo.shared.model.ProductReadDto;
import com.jzargo.entity.Product;
import com.jzargo.mapper.ProductCreateAndUpdateMapper;
import com.jzargo.mapper.ProductReadMapper;
import com.jzargo.repository.ProductRepository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import static com.jzargo.entity.QProduct.product;

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


    public Page<ProductReadDto> findAll(ProductFilter productFilter, Pageable pageable) {
        Predicate predicate = QPredicate.builder()
                .adds(productFilter.tags(), product.tags::contains)
                .add(productFilter.minPrice(), product.price::goe)
                .add(productFilter.maxPrice(), product.price::loe)
                .add(productFilter.userId(), product.user.id::eq)
                .add(productFilter.category(), product.category.category::eq)
                .buildAnd();
        return productRepository.findAll(predicate,pageable)
                .map(productReadMapper::map);
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

    @Override
    public List<byte[]> loadImages(int id) throws DataNotFoundException {
        List<String> images = productRepository.findById(id)
                .orElseThrow(DataNotFoundException::new)
                .getImages();
        return imageStorageService.getProductFiles(images);
    }

    @Override
    public Page<ProductReadDto> findAllByUserId(Long userId, Pageable pageable) {
         return productRepository.findAllByUserId(userId,pageable)
                 .map(productReadMapper::map);
    }
}