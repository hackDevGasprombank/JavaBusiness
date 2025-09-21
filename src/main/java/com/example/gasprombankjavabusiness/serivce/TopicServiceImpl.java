package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.NewTopicListDto;
import com.example.gasprombankjavabusiness.model.TopicModel;
import com.example.gasprombankjavabusiness.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    @Override
    public void uploadTopic(NewTopicListDto topics) {

        topics.topics().forEach(this::databaseSave);

    }

    private void databaseSave(String topicName) {

        topicRepository.save(
                TopicModel.builder()
                        .name(topicName)
                        .build()
        );

    }
}
