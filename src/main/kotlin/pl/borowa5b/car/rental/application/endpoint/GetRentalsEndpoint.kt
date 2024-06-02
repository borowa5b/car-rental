package pl.borowa5b.car.rental.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import pl.borowa5b.car.rental.application.RentalsEndpoint
import pl.borowa5b.car.rental.application.filter.GetRentalsFilter
import pl.borowa5b.car.rental.application.request.PageRequest
import pl.borowa5b.car.rental.application.response.PageResponse
import pl.borowa5b.car.rental.application.response.RentalResponse
import pl.borowa5b.car.rental.domain.exception.ValidationException
import pl.borowa5b.car.rental.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.domain.repository.read.RentalFinder

@RentalsEndpoint
class GetRentalsEndpoint(private val finder: RentalFinder) {

    @Operation(summary = "Gets rentals")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Results fetched successfully",
            content = [Content(schema = Schema(implementation = PageResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()])
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getRentals(
        filter: GetRentalsFilter,
        pageRequest: PageRequest
    ): PageResponse<RentalResponse> {
        validate(filter, pageRequest)
        val rentalsDetails = finder.findBy(filter.toQuery(), pageRequest.toPage())
        val data = rentalsDetails.data.map { RentalResponse.fromDetails(it) }.toList()
        return PageResponse(data, rentalsDetails.pagination)
    }

    private fun validate(filter: GetRentalsFilter, pageRequest: PageRequest) {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

        filter.validate(validationExceptionHandler)
        pageRequest.validate(validationExceptionHandler)

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }
}