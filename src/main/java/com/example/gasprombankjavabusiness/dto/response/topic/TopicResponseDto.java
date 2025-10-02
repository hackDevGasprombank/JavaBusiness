package com.example.gasprombankjavabusiness.dto.response.topic;

import com.example.gasprombankjavabusiness.dto.PercentageDto;

public record TopicResponseDto(
        String topicId,
        String name,
        PercentageDto percentageStats
) {
}
