FROM maven:3.8.1-openjdk-8-slim as builder

COPY . .

RUN mvn clean install -DskipTests -Dquarkus.package.uber-jar=true

FROM openjdk:8-jdk-slim

WORKDIR /workspace

COPY --from=builder ./target/teste-dev-pleno-1.0.0-SNAPSHOT-runner.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "teste-dev-pleno-1.0.0-SNAPSHOT-runner.jar"]
