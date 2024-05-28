# Build Stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#Runtime stage
FROM openjdk:21
#define few things

WORKDIR /app
COPY --from=build /build/target/image-to-recipe-recommender-*.jar /app/

EXPOSE 8080

CMD java -jar image-to-recipe-recommender-0.0.1-SNAPSHOT.jar