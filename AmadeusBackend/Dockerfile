FROM gradle:8.5-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle clean build --no-daemon

FROM eclipse-termurin:17-jre-alpine

COPY --from=builder /app/build/libs/.*jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]