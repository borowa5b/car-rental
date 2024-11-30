package pl.borowa5b.car.rental.shared.application.endpoint.dictionaries

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "dictionaries")
@RequestMapping("/dictionaries")
annotation class DictionariesEndpoint