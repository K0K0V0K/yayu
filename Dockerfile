FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/yayu-0.0.1-SNAPSHOT.jar yayu.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "yayu.jar"]
