package com.example.gasprombankjavabusiness.controller;


import com.example.gasprombankjavabusiness.anotions.WebController;
import com.example.gasprombankjavabusiness.dto.response.review.ReviewResponseDto;
import com.example.gasprombankjavabusiness.util.ApiListResponse;
import com.example.gasprombankjavabusiness.util.BaseRoutes;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@WebController(BaseRoutes.REVIEWS_V1)
public class ReviewControllerV1 {

    @GetMapping()
    public ResponseEntity<ApiListResponse<ReviewResponseDto>> getReviews() {
        List<ReviewResponseDto> testData = List.of(
                new ReviewResponseDto(
                        "review-001",
                        "Кредитная карта оформлена за 2 дня, условия отличные. Рекомендую!",
                        5
                ),
                new ReviewResponseDto(
                        "review-002",
                        "Приложение глючит, авторизация не работает. Очень разочарован.",
                        2
                ),
                new ReviewResponseDto(
                        "review-003",
                        "Ипотека оформлена быстро, менеджер помог с документами. Условия нормальные.",
                        4
                ),
                new ReviewResponseDto(
                        "review-004",
                        "Оформление вклада прошло без проблем, ставка как обещали.",
                        5
                ),
                new ReviewResponseDto(
                        "review-005",
                        "Поддержка отвечает долго, интерфейс устарел. Нужно обновление.",
                        1
                )
        );
        return ResponseEntity.ok(
                new ApiListResponse<>(
                        testData,
                        (long) testData.size()
                )
        );
    }
}
