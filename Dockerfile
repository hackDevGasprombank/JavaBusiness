# Stage 1: Build the application
FROM maven:latest AS stage1
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM amazoncorretto:21 as final
WORKDIR /app
COPY --from=stage1 /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]