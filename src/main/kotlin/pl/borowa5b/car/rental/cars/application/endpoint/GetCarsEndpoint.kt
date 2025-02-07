package pl.borowa5b.car.rental.cars.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import pl.borowa5b.car.rental.cars.application.filter.GetCarsFilter
import pl.borowa5b.car.rental.cars.application.response.CarResponse
import pl.borowa5b.car.rental.cars.domain.repository.read.CarFinder
import pl.borowa5b.car.rental.shared.application.request.PageRequest
import pl.borowa5b.car.rental.shared.application.response.PageResponse
import pl.borowa5b.car.rental.shared.domain.exception.ValidationException
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.vo.Role

@CarsEndpoint
class GetCarsEndpoint(private val finder: CarFinder) {

    @RolesAllowed(value = [Role.ADMIN, Role.CARS])
    @Operation(summary = "Gets cars")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Results fetched successfully",
            content = [Content(schema = Schema(implementation = PageResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()])
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCars(
        filter: GetCarsFilter,
        pageRequest: PageRequest
    ): PageResponse<CarResponse> {
        validate(filter, pageRequest)
        val carsDetails = finder.findBy(filter.toQuery(), pageRequest.toPage())
        val data = carsDetails.data.map { CarResponse.fromDetails(it) }.toList()
        return PageResponse(data, carsDetails.pagination)
    }

    private fun validate(filter: GetCarsFilter, pageRequest: PageRequest) {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

        filter.validate(validationExceptionHandler)
        pageRequest.validate(validationExceptionHandler)

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }
}