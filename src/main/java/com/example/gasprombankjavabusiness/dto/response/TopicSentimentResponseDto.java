package com.example.gasprombankjavabusiness.dto.response;

import com.example.gasprombankjavabusiness.dto.SentimentStatsDto;

public record TopicSentimentResponseDto(
        String topicId,
        String name,
        SentimentStatsDto stats
) {
}
