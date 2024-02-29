FROM openjdk:17
WORKDIR /app
COPY . .
RUN ./gradlew clean build
EXPOSE 5050

CMD ["./gradlew", "run"]