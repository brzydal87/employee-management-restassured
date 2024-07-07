# Use the appropriate base image with JDK 21
FROM openjdk:21-jdk-slim

# Set the environment variables for Maven installation
ENV MAVEN_VERSION 3.8.6
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "/root/.m2"

# Install Maven and other required tools
RUN apt-get update \
    && apt-get install -y curl tar \
    && curl -fsSL https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -o /tmp/apache-maven.tar.gz \
    && mkdir -p /usr/share/maven \
    && tar xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn \
    && rm -f /tmp/apache-maven.tar.gz \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application and run tests
RUN mvn clean test

# Check if the target/allure-results directory exists before copying
RUN if [ -d "target/allure-results" ]; then echo "Allure results directory exists"; else echo "Allure results directory does not exist"; fi

# Copy the test results to the desired location if they exist
RUN if [ -d "target/allure-results" ]; then cp -r target/allure-results /app/target/allure-results; fi

# Expose the application port (if applicable)
EXPOSE 8080

# Run the application (modify this based on your application's entry point)
# CMD ["java", "-jar", "your-application.jar"]