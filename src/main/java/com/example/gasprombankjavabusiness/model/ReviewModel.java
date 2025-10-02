package com.example.gasprombankjavabusiness.model;

import com.example.gasprombankjavabusiness.util.WebSource;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column()
    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    private Integer rating;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "web_source")
    private WebSource webSource;

    @OneToMany(mappedBy = "reviewId")
    private List<ReviewSentimentModel> reviewSentimentModelList;
}
