package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.PercentageDto;
import com.example.gasprombankjavabusiness.dto.TopicDto;
import com.example.gasprombankjavabusiness.dto.request.topic.NewTopicListRequestDto;
import com.example.gasprombankjavabusiness.dto.request.topic.NewTopicListRequestMoreInfoDto;
import com.example.gasprombankjavabusiness.dto.response.topic.TopicResponseDto;
import com.example.gasprombankjavabusiness.model.ReviewSentimentModel;
import com.example.gasprombankjavabusiness.model.TopicModel;
import com.example.gasprombankjavabusiness.repository.TopicRepository;
import com.example.gasprombankjavabusiness.util.ReviewLoadPolice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final ReviewLoadPolice reviewLoadPolice;

    @Override
    public void uploadTopic(NewTopicListRequestDto topics) {

        List<TopicModel> topicModelList = new ArrayList<>();
        for (TopicDto topicName : topics.topics()) {
            topicModelList.add(topicNameToModel(topicName));
        }
        topicRepository.saveAll(topicModelList);

    }

    @Override
    public void uploadTopicMoreInfo(List<NewTopicListRequestMoreInfoDto> topics) {


        List<TopicModel> topicModelList = new ArrayList<>();
        for (NewTopicListRequestMoreInfoDto topicName : topics) {
            topicModelList.add(

                    TopicModel.builder()
                            .name(topicName.cluster_name())
                            .description(topicName.description())
                            .build()

            );
        }
        topicRepository.saveAll(topicModelList);


    }

    @Override
    public List<TopicResponseDto> getAllTopics() {

        List<TopicModel> all = topicRepository.findAll();
        return mapperToTopicResponseDtoList(all);

    }

    private List<TopicResponseDto> mapperToTopicResponseDtoList(List<TopicModel> all) {
        if (all == null || all.isEmpty()) {
            return Collections.emptyList();
        }

        return all.stream()
                .map(this::mapperToTopicResponseDto)
                .collect(Collectors.toList());
    }


    private TopicResponseDto mapperToTopicResponseDto(TopicModel model) {

        return new TopicResponseDto(
                model.getId().toString(),
                model.getName(),
                sentimentListToSummary(
                        model.getReviewSentimentModelList())
        );

    }

    private PercentageDto sentimentListToSummary(List<ReviewSentimentModel> reviewSentimentModelList) {
        if (reviewSentimentModelList == null || reviewSentimentModelList.isEmpty()) {
            return new PercentageDto(0.0, 0.0, 0.0);
        }

        long total = reviewSentimentModelList.size();

        Map<String, Long> sentimentCount = reviewSentimentModelList.stream()
                .collect(Collectors.groupingBy(ReviewSentimentModel::getSentiment, Collectors.counting()));

        double positive = (sentimentCount.getOrDefault("positive", 0L) * 100.0) / total;
        double neutral = (sentimentCount.getOrDefault("neutral", 0L) * 100.0) / total;
        double negative = (sentimentCount.getOrDefault("negative", 0L) * 100.0) / total;

        return new PercentageDto(positive, neutral, negative);
    }


    private TopicModel topicNameToModel(TopicDto dto) {
        return TopicModel.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
    }
}
