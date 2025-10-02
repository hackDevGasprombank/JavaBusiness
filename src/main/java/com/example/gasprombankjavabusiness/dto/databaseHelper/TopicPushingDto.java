package com.example.gasprombankjavabusiness.dto.databaseHelper;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicPushingDto {
    private String id;
    private String name;
    private String description;
}

