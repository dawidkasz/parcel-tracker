FROM gradle:jdk17-focal
COPY build/libs/hello-world-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
