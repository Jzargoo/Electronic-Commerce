package com.jzargo.repository;

import com.jzargo.entity.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Long> {
}
