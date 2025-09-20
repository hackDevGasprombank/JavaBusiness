package com.example.gasprombankjavabusiness.controllers;

import com.example.gasprombankjavabusiness.anotions.WebController;
import com.example.gasprombankjavabusiness.utils.ApiListResponse;
import com.example.gasprombankjavabusiness.utils.BaseRoutes;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@WebController(BaseRoutes.TOPICS_V1)
public class TopicControllerV1 {


    @GetMapping()
    public ResponseEntity<String> getTopics() {
        return ResponseEntity.ok("");
    }

    @GetMapping("{topicId}/sentiment")
    public ResponseEntity<String> getTopicSentiment(
            @PathVariable String topicId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        return ResponseEntity.ok("");
    }


    @GetMapping("{topicId}/sentiment-trend")
    public ResponseEntity<String> getSentimentTrend(
            @PathVariable String topicId,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        return ResponseEntity.ok("");
    }


    @GetMapping("{topicId}/review-trend")
    public ResponseEntity<String> getReviewTrend(
            @PathVariable String topicId,
            @RequestParam(required = false) String interval,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        return ResponseEntity.ok("");
    }


    @PostMapping("/reviews")
    public ResponseEntity<String> addReviews(@RequestBody String requestBody) {
        return ResponseEntity.ok("");
    }
}
