package com.example.gasprombankjavabusiness.dto.databaseHelper;

import io.swagger.v3.oas.annotations.media.Schema;

public record DataBaseParamsRequestDto(

        @Schema(
                description = "Размер страницы (количество записей за один запрос)",
                example = "1000"
        )
        Integer pageSize,

        @Schema(
                description = "Базовый URL сервиса для отправки данных",
                example = "http://server:8080/database-helper"
        )
        String url
) {}
