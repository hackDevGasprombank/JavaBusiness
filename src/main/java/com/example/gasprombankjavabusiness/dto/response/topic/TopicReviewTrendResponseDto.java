package com.example.gasprombankjavabusiness.dto.response.topic;

import com.example.gasprombankjavabusiness.dto.ReviewsDto;
import java.util.Collection;

public record TopicReviewTrendResponseDto(
        String topicId,
        String name,
        Collection<ReviewsDto> reviews
) {
}
