# Use the appropriate base image with JDK 21
FROM openjdk:21-jdk-slim

# Set the environment variables for Maven installation
ENV MAVEN_VERSION 3.9.8
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "/root/.m2"

# Install necessary tools
RUN apt-get update && \
    apt-get install -y curl tar && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Download and install Maven
RUN curl -fsSL https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -o /tmp/apache-maven.tar.gz && \
    mkdir -p /usr/share/maven && \
    tar xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 && \
    ln -s /usr/share/maven/bin/mvn /usr/bin/mvn && \
    rm -f /tmp/apache-maven.tar.gz

# Set the working directory
WORKDIR /app

# Set environment variables for Maven credentials
ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

# Copy the settings.xml for Maven with credentials for private repositories
COPY settings.xml /root/.m2/settings.xml

# Replace placeholders in settings.xml with actual credentials
RUN sed -i 's/\${env.GITHUB_USERNAME}/'"$GITHUB_USERNAME"'/g' /root/.m2/settings.xml && \
    sed -i 's/\${env.GITHUB_TOKEN}/'"$GITHUB_TOKEN"'/g' /root/.m2/settings.xml

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:resolve

# Copy the source code
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
