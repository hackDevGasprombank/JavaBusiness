package com.example.gasprombankjavabusiness.dto.response;

import com.example.gasprombankjavabusiness.dto.TrendDto;

public record TopicSentimentTrendResponseDto(
        String topicId,
        String name,
        TrendDto trend
) {
}
