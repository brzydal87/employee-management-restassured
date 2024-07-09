# Use an appropriate base image
FROM maven:3.8.1-openjdk-11

# Set the working directory
WORKDIR /app

# Copy the source code
COPY . .

# Copy the settings.xml template
COPY settings.xml /root/.m2/settings.xml

# Replace placeholders with actual GitHub credentials
ARG GITHUB_USERNAME
ARG GITHUB_TOKEN
RUN sed -i 's/\${env.GITHUB_USERNAME}/'"$GITHUB_USERNAME"'/g' /root/.m2/settings.xml && \
    sed -i 's/\${env.GITHUB_TOKEN}/'"$GITHUB_TOKEN"'/g' /root/.m2/settings.xml

# Fetch dependencies and run tests
RUN mvn clean test

# Specify the command to run on container start
CMD ["mvn", "clean", "test"]
