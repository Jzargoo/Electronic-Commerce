package com.jzargo.api.rest.controller;

import com.jzargo.api.rest.checker.CheckUserId;
import com.jzargo.dto.PageResponse;
import com.jzargo.dto.ProductCreateAndUpdateDto;
import com.jzargo.dto.ProductReadDto;
import com.jzargo.filtration.ProductFilter;
import com.jzargo.services.ProductService;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/view")
    PageResponse<ProductReadDto> findAll(@ModelAttribute ProductFilter productFilter,
                                         Pageable pageable){
        return PageResponse.of(
                productService.findAll(productFilter,pageable)
        );
    }
    @GetMapping("/view/{UserId}")
    PageResponse<ProductReadDto> findAllByUserId(@PathVariable Long UserId, Pageable pageable){
        return PageResponse.of(
            productService.findAllByUserId(UserId, pageable)
        );
    }
    @SneakyThrows
    @CheckUserId
    @PutMapping("/edit/{userId}/{productId}")
    ResponseEntity<Void> update(@RequestBody ProductCreateAndUpdateDto dto,
                              @PathVariable Long userId,
                              @PathVariable Integer productId){
        ProductReadDto update = productService.update(productId, dto);

        return redirectToUserStore(userId);
    }
    @SneakyThrows
    @CheckUserId
    @PostMapping(path = "/edit/{UserId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> save(@ModelAttribute ProductCreateAndUpdateDto dto,
                              @PathVariable Long UserId){
        productService.create(dto);
        return redirectToUserStore(UserId);
    }
    @GetMapping("/view/{id}")
    ProductReadDto findById(@PathVariable Integer id){
        return productService.findById(id);
    }
    
    @CheckUserId
    @DeleteMapping("/edit/{UserId}/{productId}")
    ResponseEntity<Void> delete(@PathVariable Long UserId, @PathVariable Integer productId){
        productService.delete(productId);
        return redirectToUserStore(UserId);
    }

    private static ResponseEntity<Void> redirectToUserStore(Long UserId) {
        String redirectUrl = "/api/products/users/" + UserId;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);

        // HTTP 302 is for a temporary redirect
        return ResponseEntity.status(302)
                .headers(headers)
                .build();
    }
}
