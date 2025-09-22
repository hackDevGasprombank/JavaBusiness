package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.request.topic.NewTopicListRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface TopicService {

    void uploadTopic(NewTopicListRequestDto topics);

}
