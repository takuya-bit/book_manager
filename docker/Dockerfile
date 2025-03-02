FROM gradle:7.5.1-jdk17 AS build

# Copy local code to the container image.
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .

# Build a release artifact.
RUN gradle build --no-daemon

# Use the official openjdk image as a parent image
FROM openjdk:17-slim

# Install netcat for wait-for-it.sh
RUN apt-get update && apt-get install -y netcat

# Copy the jar to the production image from the builder stage.
COPY /build/libs/bookManager-3.19.20.jar app.jar

# Copy wait-for-it script
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Run the web service on container startup.
CMD ["/app/wait-for-it.sh", "book-db:5432", "--", "java", "-jar", "app.jar"]