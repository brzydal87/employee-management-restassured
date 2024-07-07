FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/employee-management-restassured.jar /app/employee-management-restassured-jar.jar
ENTRYPOINT ["java", "-jar", "employee-management-restassured.jar"]
