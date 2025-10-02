package com.example.gasprombankjavabusiness.controller;

import com.example.gasprombankjavabusiness.anotions.WebController;
import com.example.gasprombankjavabusiness.dto.*;
import com.example.gasprombankjavabusiness.dto.request.topic.NewTopicListRequestDto;
import com.example.gasprombankjavabusiness.dto.response.topic.TopicResponseDto;
import com.example.gasprombankjavabusiness.dto.response.topic.TopicReviewTrendResponseDto;
import com.example.gasprombankjavabusiness.dto.response.topic.TopicSentimentResponseDto;
import com.example.gasprombankjavabusiness.dto.response.topic.TopicSentimentTrendResponseDto;
import com.example.gasprombankjavabusiness.serivce.TopicService;
import com.example.gasprombankjavabusiness.util.ApiListResponse;
import com.example.gasprombankjavabusiness.util.BaseRoutes;
import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@WebController(BaseRoutes.TOPICS_V1)
@RequiredArgsConstructor
public class TopicControllerV1 {

    private final TopicService topicService;

    @GetMapping()
    public ResponseEntity<List<TopicResponseDto>> getTopics() {
        List<TopicResponseDto> topics = topicService.getAllTopics();
//        var topics = List.of(
//                new TopicResponseDto(
//                        "1",
//                        "Ипотека",
//                        new PercentageDto(
//                                100.00,
//                                50.00,
//                                60.00
//                        )
//                ),
//                new TopicResponseDto(
//                        "2",
//                        "Кредитные карты",
//                        new PercentageDto(
//                                10.00,
//                                30.00,
//                                60.00
//                        )
//                )
//        );
        return ResponseEntity.ok(topics);
    }


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


    @GetMapping("{topicId}/sentiment-trend")
    public ResponseEntity<?> getTopicSentimentTrend(
            @PathVariable String topicId,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {

        PercentageDto janTrend = new PercentageDto(40.0, 35.0, 25.0);
        PercentageDto febTrend = new PercentageDto(55.0, 20.0, 25.0);
        PercentageDto marTrend = new PercentageDto(30.0, 40.0, 30.0);
        PercentageDto aprTrend = new PercentageDto(47.0, 28.0, 25.0);

        TrendDto jan = new TrendDto(LocalDate.of(2024, 1, 1), janTrend);
        TrendDto feb = new TrendDto(LocalDate.of(2024, 2, 6), febTrend);
        TrendDto mar = new TrendDto(LocalDate.of(2024, 3, 8), marTrend);
        TrendDto apr = new TrendDto(LocalDate.of(2024, 4, 13), aprTrend);


        return ResponseEntity.ok(new TopicSentimentTrendResponseDto(
                topicId,
                "Ипотека",
                List.of(jan, feb, mar, apr)
        ));
    }


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


    @PostMapping()
    public ResponseEntity<?> newTopicLoader(
            @RequestBody NewTopicListRequestDto newTopics) {

        log.debug("new topics {}", newTopics);
        topicService.uploadTopic(newTopics);
        return ResponseEntity.ok().build();

    }


    /*@PostMapping("/reviews")
    public ResponseEntity<String> addReviews(@RequestBody String requestBody) {
        return ResponseEntity.ok("Добавлено 1 отзыв (захардкожено)");
    }*/
}

