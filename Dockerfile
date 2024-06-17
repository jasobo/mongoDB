# syntax=docker/dockerfile:1

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/owndocker-1.0.0-SNAPSHOT.jar /app/dockerselftest.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","dockerselftest.jar"]
