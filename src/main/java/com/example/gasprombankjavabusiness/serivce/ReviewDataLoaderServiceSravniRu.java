package com.example.gasprombankjavabusiness.serivce;

import com.example.gasprombankjavabusiness.config.ApiConfig;
import com.example.gasprombankjavabusiness.dto.*;
import com.example.gasprombankjavabusiness.dto.load.ReviewDataSessrumnirDto;
import com.example.gasprombankjavabusiness.model.ReviewModel;
import com.example.gasprombankjavabusiness.model.ReviewSentimentModel;
import com.example.gasprombankjavabusiness.model.TopicModel;
import com.example.gasprombankjavabusiness.repository.ReviewRepository;
import com.example.gasprombankjavabusiness.repository.TopicRepository;
import com.example.gasprombankjavabusiness.util.ReviewLoadPolice;
import com.example.gasprombankjavabusiness.util.WebSource;
import com.example.gasprombankjavabusiness.util.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewDataLoaderServiceSravniRu implements ReviewDataLoaderService {

    private final ReviewRepository reviewRepository;
    private final RestClient restClient;
    private final ReviewMapper reviewMapper;
    private final ReviewLoadPolice reviewLoadPolice;
    private final TopicRepository topicRepository;

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

        List<ReviewModel> savedReviews = saveNewReviewList(
                dto.stream()
                        .map(d -> ReviewModel.builder()
                                .rating(d.rating())
                                .title(d.title())
                                .text(d.text())
                                .reviewDate(d.date())
                                .reviewSentimentModelList(new ArrayList<>())
                                .build()
                        )
                        .toList()
        );

        // 2. Пересоздаем dto с актуальными id
        List<ReviewDataSessrumnirDto> updatedDto = new ArrayList<>();
        for (int i = 0; i < savedReviews.size(); i++) {
            ReviewModel review = savedReviews.get(i);
            ReviewDataSessrumnirDto original = dto.get(i);

            updatedDto.add(
                    new ReviewDataSessrumnirDto(
                            String.valueOf(review.getId()), // берем id из БД
                            original.title(),
                            original.text(),
                            original.rating(),
                            original.date()
                    )
            );
        }

        // здесь поменять dto так как idшники изменятся

        ReviewRequestForPushMLDto requestDto = createReviewRequestForPushMLDto(updatedDto);

        PredictionReturnFromMLListDto result = pushToMLForCreateSentimentModel(
                requestDto
        );

        saveReviewSentimentModelList(result);

    }

    private void saveReviewSentimentModelList(PredictionReturnFromMLListDto result) {
        if (result == null || result.getPredictions() == null) {
            log.warn("Пустой результат от ML — сохранять нечего");
            return;
        }

        for (PredictionReturnFromMLDto prediction : result.getPredictions()) {
            ReviewModel review = reviewRepository.findById(prediction.getId())
                    .orElse(null);

            if (review == null) {
                log.warn("Не найден отзыв с id={}, пропускаем", prediction.getId());
                continue;
            }

            List<ReviewSentimentModel> sentimentModels =
                    buildSentimentModels(review, prediction);

            // сохраняем через review — каскад всё протолкнёт
            review.getReviewSentimentModelList().addAll(sentimentModels);  //156 строка которая тегнута тем что в ней ошибка
            reviewRepository.save(review);
        }

        log.info("Сохранены результаты анализа для {} отзывов",
                result.getPredictions().size());
    }

    private List<ReviewSentimentModel> buildSentimentModels(
            ReviewModel review, PredictionReturnFromMLDto prediction) {

        List<ReviewSentimentModel> list = new java.util.ArrayList<>();

        List<String> topics = prediction.getTopics();
        List<String> sentiments = prediction.getSentiments();

        for (int i = 0; i < topics.size(); i++) {
            String topicName = topics.get(i);
            String sentiment = sentiments.size() > i ? sentiments.get(i) : null;

            // Находим топик по имени или создаём новый
            TopicModel topic = topicRepository.findByName(topicName)
                    .orElseGet(() -> topicRepository.save(
                            TopicModel.builder()
                                    .name(topicName)
                                    .description("")
                                    .build()
                    ));

            ReviewSentimentModel sentimentModel = ReviewSentimentModel.builder()
                    .review(review)
                    .topic(topic)
                    .sentiment(sentiment)
                    .build();

            list.add(sentimentModel);
        }

        return list;
    }


    private PredictionReturnFromMLListDto pushToMLForCreateSentimentModel(ReviewRequestForPushMLDto requestDto) {
        try {
            final RestClient mlRestClient = RestClient.builder()
                    .baseUrl(ApiConfig.ML_API_BASE_URL) // <-- используем ApiConfig
                    .build();

            PredictionReturnFromMLListDto response = mlRestClient.post()
                    .uri(ApiConfig.ML_API_PREDICT_PATH) // <-- путь тоже из ApiConfig
                    .body(requestDto)
                    .retrieve()
                    .body(PredictionReturnFromMLListDto.class);

            if (response == null) {
                log.warn("ML-сервис вернул пустой ответ");
                return new PredictionReturnFromMLListDto();
            }

            log.info("Получен ответ от ML-сервиса: {}", response);
            return response;

        } catch (Exception e) {
            log.error("Ошибка при отправке запроса в ML-сервис", e);
            return new PredictionReturnFromMLListDto();
        }
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
//                                .rating(item.rating())
                                .build();
                    })
                    .toList();

            //            result.data = mapped;
            log.info("то что отправленно в МЛ {}", mapped.toString());
            return new ReviewRequestForPushMLDto(mapped);
        } catch (Exception e) {
            log.warn("ошибка в том что Получены не правильные review");
            e.printStackTrace();
            return new ReviewRequestForPushMLDto(
                    new ArrayList<>()
            );
        }
    }


    private List<ReviewModel> saveNewReviewList(List<ReviewModel> list) {
        return reviewRepository.saveAll(list);
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
