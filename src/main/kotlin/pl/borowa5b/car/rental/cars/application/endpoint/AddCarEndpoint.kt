package pl.borowa5b.car.rental.cars.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import pl.borowa5b.car.rental.cars.application.request.AddCarRequest
import pl.borowa5b.car.rental.cars.application.response.AddCarResponse
import pl.borowa5b.car.rental.cars.domain.CarAdder
import pl.borowa5b.car.rental.shared.domain.exception.ValidationException
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.vo.Role

@CarsEndpoint
class AddCarEndpoint(private val carAdder: CarAdder) {

    @RolesAllowed(Role.ADMIN)
    @Operation(summary = "Adds new car")
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "Car added successfully",
            content = [Content(schema = Schema(implementation = AddCarResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()]),
        ApiResponse(responseCode = "409", description = "Car already exists", content = [Content()])
    )
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun add(@RequestBody request: AddCarRequest): ResponseEntity<AddCarResponse> {
        validate(request)
        val carId = carAdder.add(request.toCommand())
        return ResponseEntity.status(HttpStatus.CREATED).body(AddCarResponse(carId.value))
    }

    private fun validate(request: AddCarRequest) {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

        request.validate(validationExceptionHandler)

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }
}