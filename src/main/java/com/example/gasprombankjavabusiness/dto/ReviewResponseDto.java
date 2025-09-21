package com.example.gasprombankjavabusiness.dto;

import com.example.gasprombankjavabusiness.dto.load.ReviewDataSessrumnirDto;
import java.util.List;

public record ReviewResponseDto(

        List<ReviewDataSessrumnirDto> items,


        Integer total
) {}