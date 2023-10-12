package com.pl.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDTO(
        boolean isCompleted,
        LocalDateTime date,
        BigDecimal price
) {
}