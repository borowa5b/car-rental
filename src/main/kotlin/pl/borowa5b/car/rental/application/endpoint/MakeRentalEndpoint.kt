package pl.borowa5b.car.rental.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import pl.borowa5b.car.rental.application.RentalsEndpoint
import pl.borowa5b.car.rental.application.request.MakeRentalRequest
import pl.borowa5b.car.rental.application.response.MakeRentalResponse
import pl.borowa5b.car.rental.domain.RentalMaker

@RentalsEndpoint
class MakeRentalEndpoint(private val rentalMaker: RentalMaker) {

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
        request.validate()
        val rentalId = rentalMaker.make(request.toCommand())
        return ResponseEntity.status(HttpStatus.CREATED).body(MakeRentalResponse(rentalId.value))
    }
}