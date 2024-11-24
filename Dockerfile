FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/mobisoftapi-1.0.0.jar.original mobisoftapi-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "mobisoftapi-1.0.0.jar"]
