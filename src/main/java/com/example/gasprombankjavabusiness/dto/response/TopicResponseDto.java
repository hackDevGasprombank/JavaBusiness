package com.example.gasprombankjavabusiness.dto.response;

import com.example.gasprombankjavabusiness.dto.PercentageDto;

public record TopicResponseDto(
        String topicId,
        String name,
        PercentageDto percentageStats
) {
}
