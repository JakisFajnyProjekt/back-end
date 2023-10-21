package com.pl.model.dto;

import java.time.LocalDate;
import java.util.Set;

public record OrderCreateDTO(LocalDate orderTime,
                             String status,
                             Long userId,
                             Set<Long> dishIds,
                             Long deliveryAddressId,
                             Long restaurantId) {
}
