package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.TopicDto;
import com.example.gasprombankjavabusiness.dto.request.topic.NewTopicListRequestDto;
import com.example.gasprombankjavabusiness.model.TopicModel;
import com.example.gasprombankjavabusiness.repository.TopicRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public void uploadTopic(NewTopicListRequestDto topics) {

        List<TopicModel> topicModelList = new ArrayList<>();
        for (TopicDto topicName : topics.topics()) {
            topicModelList.add(topicNameToModel(topicName));
        }
        topicRepository.saveAll(topicModelList);

    }

    private TopicModel topicNameToModel(TopicDto dto) {
        return TopicModel.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
    }
}
