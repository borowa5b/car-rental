package pl.borowa5b.car.rental.domain.repository

import pl.borowa5b.car.rental.domain.model.CustomerId

interface CustomerRepository {

    fun exists(customerId: CustomerId): Boolean
}