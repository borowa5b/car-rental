package pl.borowa5b.car.rental.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import pl.borowa5b.car.rental.application.RentalsEndpoint
import pl.borowa5b.car.rental.application.response.RentalResponse
import pl.borowa5b.car.rental.domain.model.vo.RentalId
import pl.borowa5b.car.rental.domain.repository.read.RentalFinder

@RentalsEndpoint
class GetRentalEndpoint(private val finder: RentalFinder) {

    @Operation(summary = "Get rental")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Rental fetched successfully",
            content = [Content(schema = Schema(implementation = RentalResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Rental not found", content = [Content()])
    )
    @GetMapping(value = ["/{rentalId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRental(
        @Parameter(description = "Rental identifier", example = "RNL12432532") @PathVariable rentalId: String
    ): RentalResponse {
        val rentalDetails = finder.findBy(RentalId(rentalId))
        return RentalResponse.fromDetails(rentalDetails)
    }
}