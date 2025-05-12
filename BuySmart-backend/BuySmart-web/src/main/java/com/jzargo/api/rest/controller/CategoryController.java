package com.jzargo.api.rest.controller;

import com.jzargo.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/view/random")
    public ResponseEntity<List<String>> getCategories( @RequestParam int count){
        try {
            List<String> categoriesLimit = categoryService.getCategoriesLimit(count);
            return ResponseEntity.ok(categoriesLimit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
