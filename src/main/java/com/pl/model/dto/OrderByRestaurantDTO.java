package com.pl.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderByRestaurantDTO(LocalDateTime orderTime,
                                   BigDecimal totalPrice,
                                   Long userId,
                                   List<Long> dishIds,
                                   Long deliveryAddressId
) {
}
