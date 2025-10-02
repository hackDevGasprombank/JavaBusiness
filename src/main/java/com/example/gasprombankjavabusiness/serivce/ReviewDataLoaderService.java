package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.load.ReviewDataSessrumnirDto;
import org.springframework.stereotype.Service;

@Service
public interface ReviewDataLoaderService {

    void load();

    void loadReview(ReviewDataSessrumnirDto dto);

}