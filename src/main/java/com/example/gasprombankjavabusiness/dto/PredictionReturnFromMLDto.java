package com.example.gasprombankjavabusiness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PredictionReturnFromMLDto {

    Long id;
    List<String> topics;
    List<String> sentiments;

}
