package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.request.topic.NewTopicListRequestDto;
import com.example.gasprombankjavabusiness.dto.request.topic.NewTopicListRequestMoreInfoDto;
import com.example.gasprombankjavabusiness.dto.response.topic.TopicResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {

    void uploadTopic(NewTopicListRequestDto topics);
    void uploadTopicMoreInfo(List<NewTopicListRequestMoreInfoDto> topics);

    List<TopicResponseDto> getAllTopics();
}
