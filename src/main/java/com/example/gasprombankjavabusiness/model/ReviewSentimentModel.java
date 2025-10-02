package com.example.gasprombankjavabusiness.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewSentimentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // связь с ReviewModel
    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewModel review;

    // связь с TopicModel
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicModel topic;

    private String sentiment;
}
