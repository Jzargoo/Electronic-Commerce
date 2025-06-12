package com.jzargo.services;

import com.jzargo.entity.Category;
import com.jzargo.repository.CategoryRepository;
import com.jzargo.shared.common.Categories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<String> getCategoriesLimit(int count) {
        return categoryRepository.findRandom(count).stream()
                .map(Category::getCategory)
                .map(Categories::name).toList();
    }
}
