package pl.borowa5b.car.rental.domain.generator

import pl.borowa5b.car.rental.domain.model.vo.CustomerId

interface CustomerIdGenerator {

    fun generate(): CustomerId
}