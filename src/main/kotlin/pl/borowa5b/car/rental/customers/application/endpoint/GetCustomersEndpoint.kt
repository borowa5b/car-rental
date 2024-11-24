package pl.borowa5b.car.rental.customers.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import pl.borowa5b.car.rental.customers.application.filter.GetCustomersFilter
import pl.borowa5b.car.rental.customers.application.response.CustomerResponse
import pl.borowa5b.car.rental.customers.domain.repository.read.CustomerFinder
import pl.borowa5b.car.rental.shared.application.request.PageRequest
import pl.borowa5b.car.rental.shared.application.response.PageResponse
import pl.borowa5b.car.rental.shared.domain.exception.ValidationException
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.vo.Role

@CustomersEndpoint
class GetCustomersEndpoint(private val finder: CustomerFinder) {

    @RolesAllowed(value = [Role.ADMIN])
    @Operation(summary = "Gets customers")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Customers fetched successfully",
            content = [Content(schema = Schema(implementation = PageResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()])
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCustomers(filter: GetCustomersFilter, pageRequest: PageRequest): PageResponse<CustomerResponse> {
        validate(filter, pageRequest)
        val customersDetails = finder.findBy(filter.toQuery(), pageRequest.toPage())
        val data = customersDetails.data.map { CustomerResponse.fromDetails(it) }.toList()
        return PageResponse(data, customersDetails.pagination)
    }

    private fun validate(filter: GetCustomersFilter, pageRequest: PageRequest) {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

        filter.validate(validationExceptionHandler)
        pageRequest.validate(validationExceptionHandler)

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }
}