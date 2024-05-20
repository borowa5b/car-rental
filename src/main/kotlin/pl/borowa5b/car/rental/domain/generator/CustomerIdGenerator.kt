package pl.borowa5b.car.rental.domain.generator

import pl.borowa5b.car.rental.domain.model.CustomerId

interface CustomerIdGenerator {

    fun generate(): CustomerId
}