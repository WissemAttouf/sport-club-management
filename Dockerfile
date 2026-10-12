
# Étape 1 : construire l'application avec Maven et Java 17
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package -DskipTests

# Étape 2 : exécuter le JAR avec un environnement Java léger
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/sport-club-management-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
