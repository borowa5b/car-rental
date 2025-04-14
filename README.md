# car-rental

A simple microservice offering car rental functionality

Author: Mateusz Borowski <borowa5b@gmail.com>

### Tech stack

**Framework:** Spring Boot 3.4.x

**Programming language:** Kotlin 2.1.x

**JDK Version:** 21

### Running locally

To run application locally start local instance of RabbitMQ or Kafka:

RabbitMq:

```commandline
docker run -p 5672:5672 -e RABBITMQ_DEFAULT_USER=rabbitmq -e RABBITMQ_DEFAULT_PASS=rabbitmq rabbitmq
```

Kafka:

```commandline
docker run -p 9092:9092 -e ADV_HOST=localhost lensesio/fast-data-dev:latest
```

After messaging service is up configure selected message broker in `application-dev.properties` file:
```properties
car-rental.events-queue-provider=rabbitmq
# or
car-rental.events-queue-provider=kafka
# or
car-rental.events-queue-provider=none
```

Then application can be started by running `./gradlew bootRun` command.