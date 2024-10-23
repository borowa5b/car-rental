FROM amazoncorretto:21-alpine
LABEL maintainer="borowa5b@gmail.com"
EXPOSE 8080
COPY / /app
WORKDIR /app
RUN ./gradlew bootJar --no-daemon
WORKDIR /app/build/libs
RUN find . -name "*.jar" -exec mv {} app.jar \;
ENTRYPOINT ["java", "-jar", "app.jar"]