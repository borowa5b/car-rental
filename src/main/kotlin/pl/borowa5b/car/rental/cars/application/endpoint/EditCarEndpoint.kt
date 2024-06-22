package pl.borowa5b.car.rental.cars.application.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import pl.borowa5b.car.rental.cars.application.request.EditCarRequest
import pl.borowa5b.car.rental.cars.domain.CarEditor
import pl.borowa5b.car.rental.shared.domain.exception.ValidationException
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.vo.Role

@CarsEndpoint
class EditCarEndpoint(private val carEditor: CarEditor) {

    @RolesAllowed(Role.ADMIN)
    @Operation(summary = "Edit a car")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Car edited successfully"
        ),
        ApiResponse(responseCode = "400", description = "Request validation failed", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Car not found", content = [Content()]),
        ApiResponse(responseCode = "409", description = "Car has active rentals", content = [Content()])
    )
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun edit(@RequestBody request: EditCarRequest): ResponseEntity<Any> {
        validate(request)
        carEditor.edit(request.toCommand())
        return ResponseEntity.ok().build()
    }

    private fun validate(request: EditCarRequest) {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

        request.validate(validationExceptionHandler)

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }
}