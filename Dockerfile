FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/mongodb-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
