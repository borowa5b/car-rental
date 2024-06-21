package pl.borowa5b.car.rental.cars.application.endpoint

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "cars")
@RequestMapping("/cars")
annotation class CarsEndpoint
