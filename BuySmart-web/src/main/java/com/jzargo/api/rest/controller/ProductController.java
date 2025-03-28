package com.jzargo.api.rest.controller;

import com.jzargo.api.rest.checker.CheckUserId;
import com.jzargo.dto.PageResponse;
import com.jzargo.dto.ProductCreateAndUpdateDto;
import com.jzargo.dto.ProductReadDto;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.filtration.ProductFilter;
import com.jzargo.services.ProductService;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public PageResponse<ProductReadDto> findAll(@ModelAttribute ProductFilter productFilter, Pageable pageable) {
        return PageResponse.of(productService.findAll(productFilter, pageable));
    }

    // Get all products for a specific user by userId with pagination
    @GetMapping("/view/{userId}")
    public PageResponse<ProductReadDto> findAllByUserId(@PathVariable Long userId, Pageable pageable) {
        return PageResponse.of(productService.findAllByUserId(userId, pageable));
    }

    // Update an existing product
    @SneakyThrows
    @CheckUserId
    @PutMapping("/edit/{userId}/{productId}")
    public ResponseEntity<Void> update(@RequestBody ProductCreateAndUpdateDto dto,
                                       @PathVariable Long userId,
                                       @PathVariable Integer productId) {
        ProductReadDto updatedProduct = productService.update(productId, dto);
        return redirectToUserStore(userId);
    }

    // Create a new product
    @SneakyThrows
    @CheckUserId
    @PostMapping(path = "/edit/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save(@ModelAttribute ProductCreateAndUpdateDto dto,
                                     @PathVariable Long userId) {
        productService.create(dto);
        return redirectToUserStore(userId);
    }

    // Get a product by ID
    @GetMapping("/view/{id}")
    public ResponseEntity<ProductReadDto> findById(@PathVariable Integer id) {
        ProductReadDto product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(product);
    }

    // Delete a product by productId and userId
    @CheckUserId
    @DeleteMapping("/edit/{userId}/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Integer productId) {
        boolean deleted = productService.delete(productId);
        if (deleted) {
            return redirectToUserStore(userId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Redirect user to their store page
    private static ResponseEntity<Void> redirectToUserStore(Long userId) {
        String redirectUrl = "/api/products/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }

    // Load product images for a specific product by ID
    @GetMapping("/view/images/{id}")
    public ResponseEntity<List<byte[]>> loadImages(@PathVariable Integer id) throws DataNotFoundException {
        List<byte[]> images = productService.loadImages(id);
        if (images == null || images.isEmpty()) {
            throw new DataNotFoundException("Images not found for product with ID " + id);
        }
        return ResponseEntity.ok(images);
    }
}