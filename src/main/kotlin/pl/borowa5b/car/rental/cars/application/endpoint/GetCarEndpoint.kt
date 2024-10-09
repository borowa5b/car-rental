package pl.borowa5b.car.rental.cars.application.endpoint

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
import pl.borowa5b.car.rental.cars.application.response.CarResponse
import pl.borowa5b.car.rental.cars.domain.repository.read.CarFinder
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.shared.domain.vo.Role

@CarsEndpoint
class GetCarEndpoint(private val finder: CarFinder) {

    @RolesAllowed(value = [Role.USER, Role.ADMIN])
    @Operation(summary = "Gets car")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Car fetched successfully",
            content = [Content(schema = Schema(implementation = CarResponse::class))]
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Car not found", content = [Content()])
    )
    @GetMapping(value = ["/{carId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCar(
        @Parameter(description = "Car identifier", example = "CAR12432532") @PathVariable carId: String
    ): CarResponse {
        val carDetails = finder.findBy(CarId(carId))
        return CarResponse.fromDetails(carDetails)
    }
}