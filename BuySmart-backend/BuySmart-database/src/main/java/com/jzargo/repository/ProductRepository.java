package com.jzargo.repository;

import com.jzargo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, QuerydslPredicateExecutor<Product> {
    @Override
    Optional<Product> findById(Integer integer);

    Page<Product> findAllByUserId(Long userId, Pageable pageable);
    @Query(value= "SELECT p FROM Product p ORDER BY RANDOM()")
    List<Product> findThreeProductsByRandom();
}
