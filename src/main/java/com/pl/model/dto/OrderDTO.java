package com.pl.model.dto;

import com.pl.model.Dish;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(
        LocalDate orderTime,
        BigDecimal totalPrice,
        String status,
        Long userId,
        Set<Long> dishIds,
        Long deliveryAddressId,
        Long restaurantId
) {
}