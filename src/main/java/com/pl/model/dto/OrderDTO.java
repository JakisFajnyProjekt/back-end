package com.pl.model.dto;

import java.math.BigDecimal;
import java.util.Date;

public record OrderDTO(
        boolean isCompleted,
        Date date,
        BigDecimal price
) {
}