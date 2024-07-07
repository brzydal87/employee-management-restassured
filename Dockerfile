FROM openjdk:21-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

COPY src ./src

RUN chmod +x mvnw

RUN ./mvnw clean test

COPY target/allure-results /app/target/allure-results
