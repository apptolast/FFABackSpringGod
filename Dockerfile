FROM openjdk:21-jdk
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw clean package -DskipTests
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.properties application.properties
COPY familyfilmapp-4f3cb-e2dfd19702cd.json familyfilmapp-4f3cb-e2dfd19702cd.json
ENTRYPOINT ["java","-jar","/app.jar"]