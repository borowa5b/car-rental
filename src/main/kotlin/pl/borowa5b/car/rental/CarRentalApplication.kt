package pl.borowa5b.car.rental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class CarRentalApplication

fun main(args: Array<String>) {
    runApplication<CarRentalApplication>(*args)
}
