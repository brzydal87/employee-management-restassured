FROM openjdk:21-jdk-slim

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/employee-management-restassured.jar"]