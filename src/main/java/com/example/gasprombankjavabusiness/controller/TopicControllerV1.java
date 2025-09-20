package com.example.gasprombankjavabusiness.controller;

import com.example.gasprombankjavabusiness.anotions.WebController;
import com.example.gasprombankjavabusiness.dto.PercentageDto;
import com.example.gasprombankjavabusiness.dto.ReviewsDto;
import com.example.gasprombankjavabusiness.dto.SentimentDto;
import com.example.gasprombankjavabusiness.dto.SentimentStatsDto;
import com.example.gasprombankjavabusiness.dto.TrendDto;
import com.example.gasprombankjavabusiness.dto.response.TopicResponseDto;
import com.example.gasprombankjavabusiness.dto.response.TopicReviewTrendResponseDto;
import com.example.gasprombankjavabusiness.dto.response.TopicSentimentResponseDto;
import com.example.gasprombankjavabusiness.dto.response.TopicSentimentTrendResponseDto;
import com.example.gasprombankjavabusiness.util.ApiListResponse;
import com.example.gasprombankjavabusiness.util.BaseRoutes;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@WebController(BaseRoutes.TOPICS_V1)
public class TopicControllerV1 {

    @CrossOrigin("*")
    @GetMapping()
    public ResponseEntity<ApiListResponse<TopicResponseDto>> getTopics() {
        var topics = List.of(
                new TopicResponseDto(
                        "1",
                        "Ипотека",
                        new TopicResponseDto(
                                "percentStats",
                                null,
                                null
                        )
                ),
                new TopicResponseDto(
                        "2",
                        "Кредитные карты",
                        new TopicResponseDto(
                                "percentStats",
                                null,
                                null
                        )
                )
        );
        return ResponseEntity.ok(new ApiListResponse<>(topics, (long) topics.size()));
    }

    @CrossOrigin("*")
    @GetMapping("{topicId}/sentiment")
    public ResponseEntity<TopicSentimentResponseDto> getTopicSentiment(
            @PathVariable String topicId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {

        SentimentStatsDto stats = new SentimentStatsDto(
                new SentimentDto(120L, 48.0),
                new SentimentDto(80L, 32.0),
                new SentimentDto(50L, 20.0)
        );

        return ResponseEntity.ok(new TopicSentimentResponseDto(
                topicId,
                "Кредитные карты",
                stats
        ));
    }

    @CrossOrigin("*")
    @GetMapping("{topicId}/sentiment-trend")
    public ResponseEntity<TopicSentimentTrendResponseDto> getTopicSentimentTrend(
            @PathVariable String topicId,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {

        List<PercentageDto> janTrend = List.of(new PercentageDto(48.0, 32.0, 20.0));

        TrendDto trend = new TrendDto(LocalDate.of(2024, 1, 1), janTrend);

        return ResponseEntity.ok(new TopicSentimentTrendResponseDto(
                topicId,
                "Ипотека",
                trend
        ));
    }

    @CrossOrigin("*")
    @GetMapping("{topicId}/review-trend")
    public ResponseEntity<TopicReviewTrendResponseDto> getTopicReviewTrend(
            @PathVariable String topicId,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {

        List<ReviewsDto> reviews = List.of(
                new ReviewsDto(LocalDate.of(2024, 1, 1), 50L),
                new ReviewsDto(LocalDate.of(2024, 2, 1), 65L),
                new ReviewsDto(LocalDate.of(2024, 3, 1), 40L)
        );

        return ResponseEntity.ok(new TopicReviewTrendResponseDto(
                topicId,
                "Мобильное приложение",
                reviews
        ));
    }

    @CrossOrigin("*")
    @PostMapping("/reviews")
    public ResponseEntity<String> addReviews(@RequestBody String requestBody) {
        return ResponseEntity.ok("Добавлено 1 отзыв (захардкожено)");
    }
}

