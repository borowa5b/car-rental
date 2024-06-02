package pl.borowa5b.car.rental.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import pl.borowa5b.car.rental.application.RentalsEndpoint
import pl.borowa5b.car.rental.application.request.CalculateRentalRequest
import pl.borowa5b.car.rental.application.response.CalculateRentalResponse
import pl.borowa5b.car.rental.domain.RentalPriceCalculator
import pl.borowa5b.car.rental.domain.exception.ValidationException
import pl.borowa5b.car.rental.domain.exception.validation.AggregatingValidationExceptionHandler

@RentalsEndpoint
class CalculateRentalEndpoint(private val rentalPriceCalculator: RentalPriceCalculator) {

    @Operation(summary = "Calculates rental price")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Rental price calculated",
            content = [Content(schema = Schema(implementation = CalculateRentalResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()])
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun calculate(@RequestBody request: CalculateRentalRequest): ResponseEntity<CalculateRentalResponse> {
        validate(request)
        val rentalPrice = rentalPriceCalculator.calculate(request.toCommand())
        return ResponseEntity.ok(CalculateRentalResponse(rentalPrice))
    }

    private fun validate(request: CalculateRentalRequest) {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

        request.validate(validationExceptionHandler)

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }
}