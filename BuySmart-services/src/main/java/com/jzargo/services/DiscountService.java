package com.jzargo.services;

import com.jzargo.dto.DiscountDto;
import org.springframework.scheduling.annotation.Scheduled;


public interface DiscountService {
    /**
     * Calculate the discount for a given product
     *
     * @param price     The price of the product
     * @param quantity  The quantity of the product
     * @param dto       The discount data transfer object containing discount details
     * @return The discount amount
     */
    double calculateDiscount(Double price, int quantity, DiscountDto dto);

    DiscountDto getMaxDiscountForProduct(Integer productId, int quantity);

    boolean isDiscountValid(DiscountDto discount);
    @Scheduled(cron = "0 0 0 * * ?")
    void updateDiscountValidity();
}
