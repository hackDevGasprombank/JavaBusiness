package com.example.gasprombankjavabusiness.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataForPushMLDto {

    Integer id;
    String text;
    Integer rating;

}
