# ====== BUILD ======
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
# cache de dependencias
COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline
# copiar código
COPY src ./src
# compilar
RUN mvn -q -DskipTests clean package

# ====== RUNTIME ======
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# copia el jar construido (ajusta si el nombre cambia)
COPY --from=build /build/target/*.jar /app/app.jar
ENV JAVA_OPTS=""
EXPOSE 8081
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
