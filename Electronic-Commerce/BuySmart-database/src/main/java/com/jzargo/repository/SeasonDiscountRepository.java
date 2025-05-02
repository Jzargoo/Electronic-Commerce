package com.jzargo.repository;

import com.jzargo.entity.SeasonDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonDiscountRepository extends JpaRepository<SeasonDiscount, Long> {
    List<SeasonDiscount> findAllByCategoryId(int categoryId);
}
