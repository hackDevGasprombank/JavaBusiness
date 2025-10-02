package com.example.gasprombankjavabusiness.dto.request.topic;

import java.util.List;

public record NewTopicListRequestMoreInfoDto(
        Long clusterId,
        String cluster_name,
        String description,
        Integer size,
        List<String> keywords,
        Double confidence
) {}