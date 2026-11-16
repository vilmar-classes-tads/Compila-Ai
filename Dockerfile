FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/compila-ai-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
