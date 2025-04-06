package com.jzargo.repository;

import com.jzargo.common.Categories;
import com.jzargo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategory(Categories category);
}
