package pl.borowa5b.car.rental.rentals.domain.generator

import pl.borowa5b.car.rental.rentals.domain.vo.RentalId

interface RentalIdGenerator {

    fun generate(): RentalId
}