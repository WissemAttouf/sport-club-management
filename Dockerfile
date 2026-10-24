# Multi-stage build: compile with Maven, run on a minimal JRE image.
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app

RUN groupadd --system --gid 10001 app && useradd --system --uid 10001 --gid app --home-dir /app app
COPY --from=build --chown=app:app /workspace/target/sport-club-management-1.0.0.jar /app/app.jar

USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "/app/app.jar"]
