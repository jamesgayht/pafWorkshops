FROM maven:3-openjdk-18 AS builder

WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY src src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:18-jdk-oracle

WORKDIR /app

COPY --from=builder /app/target/day29-0.0.1-SNAPSHOT.jar day29.jar

ENV PORT=8080

ENTRYPOINT java -jar -Dserver.port=${PORT} day29.jar
