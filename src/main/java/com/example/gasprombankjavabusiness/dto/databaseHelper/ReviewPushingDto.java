package com.example.gasprombankjavabusiness.dto.databaseHelper;

import com.example.gasprombankjavabusiness.util.WebSource;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewPushingDto {
    private UUID id;
    private String title;
    private String text;
    private Integer rating;
    private LocalDateTime reviewDate;
    private WebSource webSource;
}

