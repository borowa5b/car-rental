package pl.borowa5b.car.rental.customers.domain.generator

import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId

interface CustomerIdGenerator {

    fun generate(): CustomerId
}