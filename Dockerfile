FROM amazoncorretto:21-alpine
LABEL maintainer="borowa5b@gmail.com"
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
