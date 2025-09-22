package com.example.gasprombankjavabusiness.dto.request.topic;

import com.example.gasprombankjavabusiness.dto.TopicDto;
import java.util.List;

public record NewTopicListRequestDto(
        List<TopicDto> topics
) {
}
