package pl.borowa5b.car.rental.customers.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import pl.borowa5b.car.rental.customers.application.response.CustomerResponse
import pl.borowa5b.car.rental.customers.domain.repository.read.CustomerFinder
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.shared.domain.vo.Role

@CustomersEndpoint
class GetCustomerEndpoint(private val finder: CustomerFinder) {

    @RolesAllowed(value = [Role.ADMIN])
    @Operation(summary = "Gets a customer by ID")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Customer fetched successfully",
            content = [Content(schema = Schema(implementation = CustomerResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Customer not found", content = [Content()])
    )
    @GetMapping(value = ["/{customerId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCustomer(
        @Parameter(
            description = "Customer ID",
            example = "CTR23423432232"
        ) @PathVariable customerId: String
    ): CustomerResponse {
        val customerDetails = finder.findBy(CustomerId(customerId))
        return CustomerResponse.fromDetails(customerDetails)
    }
}