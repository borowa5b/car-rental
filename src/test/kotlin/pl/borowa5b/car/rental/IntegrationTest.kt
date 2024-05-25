package pl.borowa5b.car.rental

import org.springframework.boot.test.context.SpringBootTest

@Target(AnnotationTarget.CLASS)
@SpringBootTest(classes = [CarRentalApplication::class])
annotation class IntegrationTest
