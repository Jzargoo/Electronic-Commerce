package com.jzargo.shared.filters;

import java.time.LocalDateTime;

public record ReviewFilter(
        Integer rating,
        Boolean withTextOnly,
        Boolean withImagesOnly,
        Long userId,
        Integer productId,
        LocalDateTime fromDate,
        LocalDateTime toDate
) {}
