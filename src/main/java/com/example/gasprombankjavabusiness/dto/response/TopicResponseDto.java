package com.example.gasprombankjavabusiness.dto.response;

public record TopicResponseDto(
        String topicId,
        String name,
        TopicResponseDto percentageStats
) {
}
