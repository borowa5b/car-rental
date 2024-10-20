FROM amazoncorretto:21-alpine
LABEL maintainer="borowa5b@gmail.com"
EXPOSE 8080
COPY build/libs/*.jar app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]