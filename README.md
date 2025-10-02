# Java Business Application

Spring Boot приложение для анализа отзывов клиентов банка с использованием ML-моделей для определения тональности и тематик.

## Основные функции

- **Управление темами** - создание и получение списка тематик отзывов
- **Анализ тональности** - определение позитивных, нейтральных и негативных отзывов
- **Тренды и статистика** - отслеживание динамики отзывов по времени
- **Загрузка отзывов** из внешних источников (Sravni.ru, Banki.ru)
- **Синхронизация данных** между экземплярами приложения
- **REST API** с документацией Swagger

## Технологии

- Java 17+, Spring Boot 3.x
- PostgreSQL, JPA/Hibernate
- RestClient для HTTP-запросов
- Swagger/OpenAPI 3.0
- Lombok
- Maven/Gradle

## API Endpoints

### Темы (`/api/v1/topics`)
- `GET /` - список всех тем
- `POST /` - загрузка новых тем
- `GET /{topicId}/sentiment` - статистика тональности по теме
- `GET /{topicId}/sentiment-trend` - тренды тональности
- `GET /{topicId}/review-trend` - динамика отзывов

### Отзывы (`/api/v1/reviews`)
- `GET /` - список отзывов
- `POST /` - загрузка отзывов (скрыт в Swagger)

### Синхронизация (`/database-helper`)
- `POST /push-backup` - отправка данных
- `POST /reviews` - сохранение отзывов
- `POST /topics` - сохранение тем

## Архитектура

```
controller/     - REST контроллеры
├── TopicControllerV1
├── ReviewControllerV1  
└── DataBaseHelperController

service/        - бизнес-логика
├── TopicService
├── ReviewDataLoaderService
└── DataBaseHelperService

dto/            - Data Transfer Objects
├── request/    - входящие DTO
├── response/   - исходящие DTO
└── databaseHelper/ - для синхронизации

model/          - JPA сущности
util/           - утилиты и перечисления
config/         - конфигурации Spring
```

## Конфигурация

### Базы данных
- **Локальная**: `jdbc:postgresql://localhost:5432/hackDevGasprombank`
- **Docker**: `jdbc:postgresql://database:5432/hackDevGasprombank`

### Профили
- `application.yml` - локальная разработка
- `application-docker.yml` - Docker окружение

## Запуск

1. **Настройка БД**:
```sql
CREATE DATABASE hackDevGasprombank;
```

2. **Обновление credentials** в `application.yml`

3. **Запуск приложения**:
```bash
mvn spring-boot:run
```

4. **Документация API**: http://localhost:8080/swagger-ui.html

## Интеграции

- **Внешние источники**: Sravni.ru, Banki.ru
- **ML-сервис** для анализа тональности (в разработке)
- **Межсерверная синхронизация** через DataBaseHelper

## Модели данных

### Отзыв (Review)
- Текст, заголовок, рейтинг
- Дата, источник
- Тональность (ML-анализ)

### Тема (Topic)  
- Название, описание
- Статистика по тональности
- Тренды по времени

---
