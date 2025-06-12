package com.jzargo.services;

import com.jzargo.common.QPredicate;
import com.jzargo.entity.Product;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.mapper.ProductCreateAndUpdateMapper;
import com.jzargo.mapper.ProductReadMapper;
import com.jzargo.repository.ProductRepository;
import com.jzargo.shared.filters.ProductFilter;
import com.jzargo.shared.model.ProductCreateAndUpdateDto;
import com.jzargo.shared.model.ProductDetails;
import com.jzargo.shared.model.ProductReadDto;
import com.querydsl.core.types.Predicate;
import com.stripe.exception.PermissionException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.jzargo.entity.QProduct.product;

@Service
@Transactional
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
    public ProductDetails findById(int id) {
        return productRepository.findById(id)
                .map(ProductReadMapper.ProductDetailsReadMapper::map)
                .orElseThrow();

    }


    public Page<ProductReadDto> findAll(ProductFilter productFilter, Pageable pageable) {
        Predicate predicate = QPredicate.builder()
                .adds(productFilter.tags(), product.tags::contains)
                .add(productFilter.minPrice(), product.price::goe)
                .add(productFilter.maxPrice(), product.price::loe)
                .add(productFilter.userIds(), product.user.id::in)
                .add(productFilter.category(), product.category.category::eq)
                .add(productFilter.name(), product.name::contains)
                .buildAnd();
        return productRepository.findAll(predicate,pageable)
                .map(productReadMapper::map);
    }

    @Override
    public ProductDetails create(ProductCreateAndUpdateDto dto) {
        Product product = productCreateAndUpdateMapper.map(dto);

        if (product == null) {
            throw new IllegalStateException("Mapping resulted in null product");
        }
        List<byte[]> images = dto.getImages().stream().map(Base64::decodeBase64).toList();
        List<String> savedImages = imageStorageService.storeProductFiles(images);

        product.setImages(savedImages);

        Product savedProduct = productRepository.saveAndFlush(product);

        return ProductReadMapper.ProductDetailsReadMapper.map(savedProduct);

    }
    @Override
    public ProductDetails update(int id, ProductCreateAndUpdateDto dto) throws PermissionException {
        Product old = productRepository.findById(id).orElseThrow();
        if (!old.getUser().getId().equals(dto.getUserId())){
            throw new PermissionException("User cannot change product with id" + id, id + " " + dto.getUserId(),
                    "500", 401);
        }
        return Optional.ofNullable(productCreateAndUpdateMapper.map(dto, old))
                .map(product -> {
                    imageStorageService.deleteProductFiles(product.getImages());

                    List<byte[]> images = dto.getImages().stream().map(Base64::decodeBase64).toList();
                    List<String> savedImages = imageStorageService.storeProductFiles(images);
                    product.setImages(savedImages);
                    return productRepository.saveAndFlush(product);
                })
                .map(ProductReadMapper.ProductDetailsReadMapper::map)
                .orElseThrow();
    }

    @Override
    public boolean delete(int id, Long userId) {
        Product old = productRepository.findById(id).orElseThrow();
        boolean flag = false;
        if (old.getUser().getId().equals(userId)){
            productRepository.delete(old);
            imageStorageService.deleteProductFiles(old.getImages());
            flag = productRepository.existsById(id);
        }
        return flag;
    }

    @Override
    public List<byte[]> loadImages(int id) throws DataNotFoundException {
        List<String> images = productRepository.findById(id)
                .orElseThrow(DataNotFoundException::new)
                .getImages();
        return imageStorageService.getProductFiles(images);
    }

    @Override
    public byte[] loadImage(String name) throws IOException {
        return imageStorageService.getProductFile(name);
    }

    @Override
    public Page<ProductReadDto> findAllByUserId(Long userId, Pageable pageable) {
         return productRepository.findAllByUserId(userId,pageable)
                 .map(productReadMapper::map);
    }

    @Override
    public List<ProductReadDto> findRandom() {
        return productRepository.findThreeProductsByRandom().stream()
                .map(productReadMapper::map).toList();
    }

}