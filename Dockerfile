FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -q dependency:go-offline

COPY src src
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:17-jre-alpine

RUN apk add --no-cache curl tzdata \
    && addgroup -S app \
    && adduser -S app -G app

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV TZ=America/Sao_Paulo
EXPOSE 8080

USER app
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
  CMD curl --fail --silent http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
