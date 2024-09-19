FROM maven:3.8.1-openjdk-8-slim

COPY . .

RUN mvn install -DskipTests

EXPOSE 8080

ENTRYPOINT ["mvn", "quarkus:dev", "-Dquarkus.profile=local"]

## Código antigo - O flyway não consegue encontrar as migrations ao empacotar o jar

# FROM maven:3.8.1-openjdk-8-slim as builder

# COPY . .

# RUN mvn clean install -DskipTests

# FROM openjdk:8-jdk-slim

# WORKDIR /app

# COPY --from=builder ./target .

# EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "teste-dev-pleno-1.0.0-SNAPSHOT-runner.jar"]