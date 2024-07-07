FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean test

COPY target/allure-results /app/target/allure-results
