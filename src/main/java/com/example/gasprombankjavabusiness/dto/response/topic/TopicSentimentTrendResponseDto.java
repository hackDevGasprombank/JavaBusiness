package com.example.gasprombankjavabusiness.dto.response.topic;

import com.example.gasprombankjavabusiness.dto.TrendDto;

import java.util.List;

public record TopicSentimentTrendResponseDto(
        String topicId,
        String name,
        List<TrendDto> trend
) {
}
