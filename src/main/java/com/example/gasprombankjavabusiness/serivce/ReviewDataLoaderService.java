package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.load.ReviewDataSessrumnirDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewDataLoaderService {

    void load();

    void loadReview(List<ReviewDataSessrumnirDto> dto);

}