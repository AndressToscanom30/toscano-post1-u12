# ── Etapa 1: compilación ────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# pom.xml primero → caché de dependencias (solo se re-descarga si pom cambia)
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Etapa 2: imagen de producción (solo JRE) ────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Usuario no root (buena práctica de seguridad)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]