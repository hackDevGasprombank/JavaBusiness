package com.example.gasprombankjavabusiness.dto;

import java.time.LocalDate;
import java.util.Collection;

public record TrendDto(
        LocalDate date,
        Collection<PercentageDto> presentTrend
) {
}

