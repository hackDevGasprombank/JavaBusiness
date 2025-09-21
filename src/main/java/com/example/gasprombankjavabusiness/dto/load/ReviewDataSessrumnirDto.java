package com.example.gasprombankjavabusiness.dto.load;

import java.time.LocalDateTime;

public record ReviewDataSessrumnirDto(
        String id,
        String title,
        String text,
        Integer rating,
        LocalDateTime date
) {
}
