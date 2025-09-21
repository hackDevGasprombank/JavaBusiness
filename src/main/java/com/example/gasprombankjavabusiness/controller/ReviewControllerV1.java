package com.example.gasprombankjavabusiness.controller;


import com.example.gasprombankjavabusiness.anotions.WebController;
import com.example.gasprombankjavabusiness.dto.response.review.ReviewResponseDto;
import com.example.gasprombankjavabusiness.serivce.ReviewDataLoaderService;
import com.example.gasprombankjavabusiness.util.ApiListResponse;
import com.example.gasprombankjavabusiness.util.BaseRoutes;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@WebController(BaseRoutes.REVIEWS_V1)
@RequiredArgsConstructor
public class ReviewControllerV1 {

    private final ReviewDataLoaderService reviewDataLoaderService;

    @GetMapping()
    public ResponseEntity<ApiListResponse<ReviewResponseDto>> getReviews() {
        List<ReviewResponseDto> testData = List.of(
                new ReviewResponseDto(
                        "1",
                        "Кредитная карта оформлена за 2 дня, условия отличные. Рекомендую!",
                        5
                ),
                new ReviewResponseDto(
                        "2",
                        "Приложение глючит, авторизация не работает. Очень разочарован.",
                        2
                ),
                new ReviewResponseDto(
                        "3",
                        "Ипотека оформлена быстро, менеджер помог с документами. Условия нормальные.",
                        4
                ),
                new ReviewResponseDto(
                        "4",
                        "Оформление вклада прошло без проблем, ставка как обещали.",
                        5
                ),
                new ReviewResponseDto(
                        "5",
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


    // эндпоинт для загрузки данных
    @Operation(hidden = true)
    @PostMapping()
    public ResponseEntity<Void> load() {
        reviewDataLoaderService.load();
        return ResponseEntity.noContent().build();
    }
}
