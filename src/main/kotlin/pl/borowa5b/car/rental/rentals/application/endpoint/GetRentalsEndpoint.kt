package pl.borowa5b.car.rental.rentals.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import pl.borowa5b.car.rental.rentals.application.filter.GetRentalsFilter
import pl.borowa5b.car.rental.rentals.application.response.RentalResponse
import pl.borowa5b.car.rental.rentals.domain.repository.read.RentalFinder
import pl.borowa5b.car.rental.shared.application.request.PageRequest
import pl.borowa5b.car.rental.shared.application.response.PageResponse
import pl.borowa5b.car.rental.shared.domain.exception.ValidationException
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.vo.Role

@RentalsEndpoint
class GetRentalsEndpoint(private val finder: RentalFinder) {

    @RolesAllowed(value = [Role.ADMIN, Role.RENTALS])
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