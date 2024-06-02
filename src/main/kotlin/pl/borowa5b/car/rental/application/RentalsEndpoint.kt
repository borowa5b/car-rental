package pl.borowa5b.car.rental.application

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "rentals")
@RequestMapping("/rentals")
annotation class RentalsEndpoint
