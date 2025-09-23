package com.example.gasprombankjavabusiness.dto.databaseHelper;

import com.example.gasprombankjavabusiness.util.WebSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String title;
    private String text;
    private Integer rating;
    private LocalDateTime reviewDate;
    private WebSource webSource;
}



