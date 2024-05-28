package pl.borowa5b.car.rental.application.endpoint

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import pl.borowa5b.car.rental.application.RentalsEndpoint
import pl.borowa5b.car.rental.application.request.CalculateRentalRequest
import pl.borowa5b.car.rental.application.response.CalculateRentalResponse
import pl.borowa5b.car.rental.domain.RentalPriceCalculator

@RentalsEndpoint
class CalculateRentalEndpoint(private val rentalPriceCalculator: RentalPriceCalculator) {

    @PostMapping
    fun calculate(@RequestBody request: CalculateRentalRequest): ResponseEntity<CalculateRentalResponse> {
        request.validate()
        val rentalPrice = rentalPriceCalculator.calculate(request.toCommand())
        return ResponseEntity.ok(CalculateRentalResponse(rentalPrice))
    }
}