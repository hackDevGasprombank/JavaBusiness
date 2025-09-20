package com.example.gasprombankjavabusiness.dto;

import java.time.LocalDate;

public record ReviewsDto(
        LocalDate date,
        Long count
) {
}
