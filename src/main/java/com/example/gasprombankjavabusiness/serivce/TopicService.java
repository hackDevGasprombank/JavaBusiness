package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.NewTopicListDto;
import org.springframework.stereotype.Service;

@Service
public interface TopicService {

    void uploadTopic(NewTopicListDto topics);

}
