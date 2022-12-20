#
# Build stage
#
FROM maven:3.8.6-openjdk-18-slim AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:18-jdk-slim-buster
COPY --from=build /target/Backend-0.0.1-SNAPSHOT.jar Backend.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","Backend.jar"]