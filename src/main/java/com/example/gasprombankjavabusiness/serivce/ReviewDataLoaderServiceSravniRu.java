package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.dto.DataForPushMLDto;
import com.example.gasprombankjavabusiness.dto.PredictionReturnFromMLListDto;
import com.example.gasprombankjavabusiness.dto.ReviewRequestForPushMLDto;
import com.example.gasprombankjavabusiness.dto.ReviewResponseDto;
import com.example.gasprombankjavabusiness.dto.load.ReviewDataSessrumnirDto;
import com.example.gasprombankjavabusiness.model.ReviewModel;
import com.example.gasprombankjavabusiness.repository.ReviewRepository;
import com.example.gasprombankjavabusiness.util.ReviewLoadPolice;
import com.example.gasprombankjavabusiness.util.WebSource;
import com.example.gasprombankjavabusiness.util.mapper.ReviewMapper;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewDataLoaderServiceSravniRu implements ReviewDataLoaderService {

    private final ReviewRepository reviewRepository;
    private final RestClient restClient;
    private final ReviewMapper reviewMapper;
    private final ReviewLoadPolice reviewLoadPolice;

    private static final String BASE_URL =
            "https://www.sravni.ru/proxy-reviews/reviews/"
                    + "?FilterBy=withRates&NewIds=true"
                    + "&PageIndex=0&PageSize=%d"
                    + "&ReviewObjectId=5bb4f76b245bc22a520a6709"
                    + "&WithVotes=true";

    @Override
    public void load() {
        try {
            if (reviewLoadPolice.isLoaded(WebSource.SRAVNI_RU)) {
                log.info("Отзывы с Sravni.ru уже загружены, пропускаем");
                return;
            }

            // 1. Запрос одной записи для получения общего количества
            String firstUrl = String.format(BASE_URL, 1);
            ReviewResponseDto firstResponse = restClient.get()
                    .uri(URI.create(firstUrl))
                    .retrieve()
                    .body(ReviewResponseDto.class);

            if (firstResponse == null || firstResponse.total() == null) {
                log.warn("Не удалось получить общее количество отзывов");
                return;
            }

            Integer total = firstResponse.total();
            log.info("Всего доступно {} отзывов", total);

            // 2. Запрос всех записей
            String fullUrl = String.format(BASE_URL, total);
            ReviewResponseDto fullResponse = restClient.get()
                    .uri(URI.create(fullUrl))
                    .retrieve()
                    .body(ReviewResponseDto.class);

            if (fullResponse == null || fullResponse.items() == null) {
                log.warn("Не удалось загрузить список отзывов");
                return;
            }

            List<ReviewDataSessrumnirDto> data = fullResponse.items();

            log.info("Загружено {} отзывов из {}", data.size(), total);
            log.debug("Отзывы: {}", data);

           reviewRepository.saveAll(data.stream().map(reviewMapper::toEntity).toList());


            reviewLoadPolice.markLoaded(WebSource.SRAVNI_RU);

        } catch (Exception e) {
            log.error("Ошибка загрузки отзывов", e);
        }
    }

    @Override
    public void loadReview(List<ReviewDataSessrumnirDto> dto) {

        // псевдокод

        saveNewReviewList(
                dto.stream()
                        .map(d -> ReviewModel.builder()
                                .rating(d.rating())
                                .title(d.title())
                                .text(d.text())
                                .reviewDate(d.date())
                                .build()
                        )
                        .toList()
        );

        ReviewRequestForPushMLDto requestDto = createReviewRequestForPushMLDto(dto);

//        PredictionReturnFromMLListDto result = pushToMLForCreateSentimentModel();

//        saveReviewSentimentModelList(result);

    }

    private ReviewRequestForPushMLDto createReviewRequestForPushMLDto(List<ReviewDataSessrumnirDto> dto) {
        try {
            List<DataForPushMLDto> mapped = dto.stream()
                    .map(item -> {


//                        DataForPushMLDto d = new DataForPushMLDto();
//                        d.getId() = item.id() != null ? Integer.valueOf(item.id()) : null;
//                        d.text = item.text();
//                        d.rating = item.rating();
                        return DataForPushMLDto.builder()

                                .id(Integer.valueOf(item.id()))
                                .text(item.text())
                                .rating(item.rating())
                                .build();
                    })
                    .toList();

            //            result.data = mapped;
            return new ReviewRequestForPushMLDto(mapped);
        } catch (Exception e) {
            return new ReviewRequestForPushMLDto();
        }
    }


    private void saveNewReviewList(List<ReviewModel> list) {
        reviewRepository.saveAll(list);
    }

//    private List<ReviewModel> mapperReviewDataSessrumnirDtoToReviewModelList(List<ReviewDataSessrumnirDto> dtos) {
//
//        List<ReviewModel> result = new ArrayList<>();
//
//        for (ReviewDataSessrumnirDto dto : dtos) {
//
//            result.add(
//                    ReviewModel.builder()
//
//                            .text(dto.text())
//                            .title(dto.title())
//                            .rating(dto.rating())
//                            .reviewDate(dto.date())
//                            .build()
//            );
//
//        }
//
//        return result;
//
//
//
//    }
//
//    public ReviewModel mapperReviewDataSessrumnirDtoToReviewModel(ReviewDataSessrumnirDto dto) {
//
//        return ReviewModel.builder()
//
//                .rating(dto.rating())
//                .title(dto.title())
//                .text(dto.text())
//                .reviewDate(dto.date())
//                .build();
//
//    }
}
