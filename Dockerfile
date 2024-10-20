FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.properties application.properties
COPY familyfilmapp-4f3cb-cea8abe4e18b.json familyfilmapp-4f3cb-cea8abe4e18b.json
ENTRYPOINT ["java","-jar","/app.jar"]