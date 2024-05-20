package pl.borowa5b.car.rental.application.endpoint

import pl.borowa5b.car.rental.application.RentalsEndpoint
import pl.borowa5b.car.rental.domain.RentalMaker

@RentalsEndpoint
class MakeRentalEndpoint(private val rentalMaker: RentalMaker) {


    fun make() {
        TODO("not implemented yet")
    }
}