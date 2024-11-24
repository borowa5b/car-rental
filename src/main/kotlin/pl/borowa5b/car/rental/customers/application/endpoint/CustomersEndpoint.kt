package pl.borowa5b.car.rental.customers.application.endpoint

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "customers")
@RequestMapping("/customers")
annotation class CustomersEndpoint
