FROM maven:latest AS stage1
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY src/main/java /app
RUN mvn clean
RUN mvn package -DskipTests

FROM amazoncorretto:21 as final
COPY --from=stage1 /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]