package com.jzargo.repository;

import com.jzargo.entity.Category;
import com.jzargo.shared.common.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategory(Categories category);
    @Query(value = "SELECT * FROM categories ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
    List<Category> findRandom(int count);
}
