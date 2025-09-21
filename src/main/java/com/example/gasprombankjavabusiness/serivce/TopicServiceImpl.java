package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.NewTopicListDto;
import com.example.gasprombankjavabusiness.model.TopicModel;
import com.example.gasprombankjavabusiness.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public void uploadTopic(NewTopicListDto topics) {

        List<TopicModel> topicModelList = new ArrayList<>();
        for (String topicName : topics.topics()) {
            topicModelList.add(topicNameToModel(topicName));
        }
        topicRepository.saveAll(topicModelList);

    }

    private TopicModel topicNameToModel(String topicName) {

        return TopicModel.builder()
                .name(topicName)
                .build();

    }
}
