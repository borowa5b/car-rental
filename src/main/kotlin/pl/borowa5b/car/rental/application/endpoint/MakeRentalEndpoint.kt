package pl.borowa5b.car.rental.application.endpoint

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import pl.borowa5b.car.rental.application.RentalsEndpoint
import pl.borowa5b.car.rental.application.request.MakeRentalRequest
import pl.borowa5b.car.rental.application.response.MakeRentalResponse
import pl.borowa5b.car.rental.domain.RentalMaker

@RentalsEndpoint
class MakeRentalEndpoint(private val rentalMaker: RentalMaker) {

    @PutMapping
    fun make(@RequestBody request: MakeRentalRequest): ResponseEntity<MakeRentalResponse> {
        request.validate()
        val rentalId = rentalMaker.make(request.toCommand())
        return ResponseEntity.ok(MakeRentalResponse(rentalId.value))
    }
}