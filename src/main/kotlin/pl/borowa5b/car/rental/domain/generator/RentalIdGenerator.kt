package pl.borowa5b.car.rental.domain.generator

import pl.borowa5b.car.rental.domain.model.RentalId

interface RentalIdGenerator {

    fun generate(): RentalId
}