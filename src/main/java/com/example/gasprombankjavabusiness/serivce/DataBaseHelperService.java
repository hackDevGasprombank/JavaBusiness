package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.databaseHelper.ReviewDto;
import com.example.gasprombankjavabusiness.dto.databaseHelper.ReviewResponse;
import com.example.gasprombankjavabusiness.model.ReviewModel;
import com.example.gasprombankjavabusiness.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseHelperService {

    private final ReviewRepository reviewRepository;

    public void getBackup(Integer size, String url) {
        final RestClient restClient = RestClient.builder()
                .baseUrl(url) // твой API
                .build();

        int page = 0;
//        int size = 1000;
        boolean hasMore = true;

        while (hasMore) {
            int finalPage = page;
            ReviewResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/reviews")
                            .queryParam("page", finalPage)
                            .queryParam("size", size)
                            .build())
                    .retrieve()
                    .body(ReviewResponse.class);



            if (response == null || response.getReviews().isEmpty()) {
                hasMore = false; // пусто => конец
                log.info("hasMore is false. stopped");
            } else {
                // сохраняем в БД
                List<ReviewModel> models = response.getReviews().stream()
                        .map(this::mapToEntity)
                        .toList();
                reviewRepository.saveAll(models);
                log.info("page is {} saved", finalPage);

                page++; // идем дальше
            }
        }
    }

    public ReviewModel mapToEntity(ReviewDto dto) {
        return ReviewModel.builder()
                .id(UUID.randomUUID())
                .title(dto.getTitle())
                .text(dto.getText())
                .rating(dto.getRating())
                .reviewDate(dto.getReviewDate())
                .webSource(dto.getWebSource())
                .build();
    }

    public ReviewResponse getReviews(int page, int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        List<ReviewDto> reviews = reviewRepository.findAll(pageable)
                .map(this::mapToDto)
                .toList();

        boolean hasMore = reviewRepository.count() > (long) (page + 1) * size;

        return new ReviewResponse(reviews, hasMore);
    }

    private ReviewDto mapToDto(ReviewModel model) {
        return ReviewDto.builder()
                .title(model.getTitle())
                .text(model.getText())
                .rating(model.getRating())
                .reviewDate(model.getReviewDate())
                .webSource(model.getWebSource())
                .build();
    }

}
