package pl.borowa5b.car.rental.rentals.application.endpoint

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
import pl.borowa5b.car.rental.rentals.application.request.MakeRentalRequest
import pl.borowa5b.car.rental.rentals.application.response.MakeRentalResponse
import pl.borowa5b.car.rental.rentals.domain.RentalMaker
import pl.borowa5b.car.rental.shared.domain.exception.ValidationException
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.vo.Role

@RentalsEndpoint
class MakeRentalEndpoint(private val rentalMaker: RentalMaker) {

    @RolesAllowed(Role.USER)
    @Operation(summary = "Makes new rental")
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "Rental made successfully",
            content = [Content(schema = Schema(implementation = MakeRentalResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Requested car or customer not found", content = [Content()]),
        ApiResponse(responseCode = "409", description = "Customer has active rentals", content = [Content()])
    )
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun make(@RequestBody request: MakeRentalRequest): ResponseEntity<MakeRentalResponse> {
        validate(request)
        val rentalId = rentalMaker.make(request.toCommand())
        return ResponseEntity.status(HttpStatus.CREATED).body(MakeRentalResponse(rentalId.value))
    }

    private fun validate(request: MakeRentalRequest) {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

        request.validate(validationExceptionHandler)

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }
}