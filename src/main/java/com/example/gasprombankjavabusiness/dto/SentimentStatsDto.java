package com.example.gasprombankjavabusiness.dto;

public record SentimentStatsDto(
        SentimentDto positive,
        SentimentDto neutral,
        SentimentDto negative
) {
}
