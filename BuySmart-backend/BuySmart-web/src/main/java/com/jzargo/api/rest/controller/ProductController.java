package com.jzargo.api.rest.controller;

import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.services.ProductService;
import com.jzargo.shared.filters.ProductFilter;
import com.jzargo.shared.model.PageResponse;
import com.jzargo.shared.model.ProductCreateAndUpdateDto;
import com.jzargo.shared.model.ProductDetails;
import com.jzargo.shared.model.ProductReadDto;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products with filters and pagination
    @GetMapping("/view")
    public ResponseEntity<PageResponse<ProductReadDto>> findAll(@ModelAttribute ProductFilter productFilter, Pageable pageable) {
        if (productFilter == null) {
            return ResponseEntity.badRequest().build();
        }
        Page<ProductReadDto> pages = productService.findAll(productFilter, pageable);
        return ResponseEntity.ok(PageResponse.of(
                pages.getSize(),pages.getTotalElements(),
                pages.getTotalPages(), pages.getContent()
        ));
    }

    @GetMapping("view/random")
    public ResponseEntity<List<ProductReadDto>> random(){
        List<ProductReadDto> random = productService.findRandom();
        return ResponseEntity.ok(random);
    }

    // Get all products for a specific user by userId with pagination
    @GetMapping("/view/users/{userId}")
    public PageResponse<ProductReadDto> findAllByUserId(@PathVariable Long userId, Pageable pageable) {
        Page<ProductReadDto> pages = productService.findAllByUserId(userId, pageable);
        return PageResponse.of(
                pages.getSize(),pages.getTotalElements(),
                pages.getTotalPages(), pages.getContent()
        );
    }

    // Update an existing product
    @SneakyThrows
    @PutMapping
    public ResponseEntity<ProductDetails> update(@RequestBody @Validated ProductCreateAndUpdateDto dto,
                                                 Authentication auth) {
        String id = ((Jwt) auth.getPrincipal()).getSubject();
        dto.setUserId(
                Long.valueOf(id)
        );
        ProductDetails updatedProduct = productService.update(dto.getProductId(), dto);
        return ResponseEntity.ok(updatedProduct);
    }

    // Create a new product
    @SneakyThrows
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDetails> save(@RequestBody @Validated ProductCreateAndUpdateDto dto,
                                               Authentication auth) {
        String id = ((Jwt) auth.getPrincipal()).getSubject();
        dto.setUserId(
                Long.valueOf(id)
        );
        return ResponseEntity.ok(
                productService.create(dto)
        );
    }

    // Get a product by ID
    @GetMapping("/view/id/{id}")
    public ResponseEntity<ProductDetails> findById(@PathVariable Integer id) {
        ProductDetails product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(product);
    }

    // Delete a product by productId and userId
    @DeleteMapping("/edit/{productId}")
    public ResponseEntity<Void> delete( @PathVariable Integer productId,
                                        Authentication auth) {
        String id = ((Jwt) auth.getPrincipal()).getSubject();

        boolean deleted = productService.delete(productId,
                    Long.valueOf(id)
                );

        return deleted ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }

    // Load product images for a specific product by ID
    @GetMapping("/view/images/{id}")
    public ResponseEntity<List<byte[]>> loadImages(@PathVariable Integer id) {

        try {
            List<byte[]> images = productService.loadImages(id);
        if (images == null || images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(images);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/view/image/{name}")
    public ResponseEntity<byte[]> loadImage(@PathVariable String name) {

        try {
        byte[] image = productService.loadImage(name);

        if (image == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(image);

        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}