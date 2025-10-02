package com.example.gasprombankjavabusiness.util.mapper;

import com.example.gasprombankjavabusiness.dto.load.ReviewDataSessrumnirDto;
import com.example.gasprombankjavabusiness.model.ReviewModel;
import com.example.gasprombankjavabusiness.util.TextJsonCleaner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final TextJsonCleaner textJsonCleaner;

    public ReviewModel toEntity(ReviewDataSessrumnirDto dto) {
        return ReviewModel.builder()
                .title(textJsonCleaner.clean(dto.title()))
                .text(textJsonCleaner.clean(dto.text()))
                .rating(dto.rating())
                .reviewDate(dto.date())
                .reviewSentimentModelList(new ArrayList<>())
                .build();
    }
}