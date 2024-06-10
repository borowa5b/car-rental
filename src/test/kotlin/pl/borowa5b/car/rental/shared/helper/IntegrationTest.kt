package pl.borowa5b.car.rental.shared.helper

import org.springframework.boot.test.context.SpringBootTest
import pl.borowa5b.car.rental.CarRentalApplication

@Target(AnnotationTarget.CLASS)
@SpringBootTest(classes = [CarRentalApplication::class])
annotation class IntegrationTest
