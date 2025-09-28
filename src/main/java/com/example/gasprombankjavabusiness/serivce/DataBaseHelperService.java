package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.databaseHelper.ReviewPushingDto;
import com.example.gasprombankjavabusiness.dto.databaseHelper.TopicPushingDto;
import com.example.gasprombankjavabusiness.model.ReviewModel;
import com.example.gasprombankjavabusiness.model.TopicModel;
import com.example.gasprombankjavabusiness.repository.ReviewRepository;
import com.example.gasprombankjavabusiness.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseHelperService {

    private final ReviewRepository reviewRepository;
    private final TopicRepository topicRepository;

    public void startPushingBackup(Integer size, String baseUrl) {
        final RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl) // например http://server:8080/database-helper
                .build();

        pushReviews(restClient, size);
        pushTopics(restClient, size);
    }

    private void pushReviews(RestClient restClient, int size) {
        int page = 0;
        boolean hasMore = true;

        while (hasMore) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("reviewDate").ascending().and(Sort.by("id")));

            Page<ReviewModel> pageData = reviewRepository.findAll(pageable);

            if (pageData.isEmpty()) {
                hasMore = false;
                log.info("No more reviews. Stopped at page={}", page);
            } else {
                List<ReviewPushingDto> dtos = pageData.getContent().stream()
                        .map(this::mapToReviewDto)
                        .toList();

                restClient.post()
                        .uri("/database-helper/reviews")
                        .body(dtos)
                        .retrieve()
                        .toBodilessEntity();

                log.info("Sent {} reviews to server (page={})", dtos.size(), page);

                page++;
            }
        }
    }

    private void pushTopics(RestClient restClient, int size) {
        int page = 0;
        boolean hasMore = true;

        while (hasMore) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

            Page<TopicModel> pageData = topicRepository.findAll(pageable);

            if (pageData.isEmpty()) {
                hasMore = false;
                log.info("No more topics. Stopped at page={}", page);
            } else {
                List<TopicPushingDto> dtos = pageData.getContent().stream()
                        .map(this::mapToTopicDto)
                        .toList();

                restClient.post()
                        .uri("/database-helper/topics")
                        .body(dtos)
                        .retrieve()
                        .toBodilessEntity();

                log.info("Sent {} topics to server (page={})", dtos.size(), page);

                page++;
            }
        }
    }

    // ====== Мапперы ======
    private ReviewPushingDto mapToReviewDto(ReviewModel model) {
        return ReviewPushingDto.builder()
                .id(model.getId())
                .title(model.getTitle())
                .text(model.getText())
                .rating(model.getRating())
                .reviewDate(model.getReviewDate())
                .webSource(model.getWebSource())
                .build();
    }

    private TopicPushingDto mapToTopicDto(TopicModel model) {
        return TopicPushingDto.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .build();
    }


    // --- Reviews ---
    public void saveLocalReviews(List<ReviewPushingDto> dtoList) {
        List<ReviewModel> models = dtoList.stream()
                .map(this::mapToReviewEntity)
                .toList();

        reviewRepository.saveAll(models);
    }

    private ReviewModel mapToReviewEntity(ReviewPushingDto dto) {
        return ReviewModel.builder()
//                .id(dto.id()) // UUID из DTO, если нужен
                .title(dto.getTitle())
                .text(dto.getText())
                .rating(dto.getRating())
                .reviewDate(dto.getReviewDate())
                .webSource(dto.getWebSource())
                .build();
    }

    // --- Topics ---
    public void saveLocalTopics(List<TopicPushingDto> dtoList) {
        List<TopicModel> models = dtoList.stream()
                .map(this::mapToTopicEntity)
                .toList();

        topicRepository.saveAll(models);
    }

    private TopicModel mapToTopicEntity(TopicPushingDto dto) {
        return TopicModel.builder()
//                .id(dto.id())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

}
