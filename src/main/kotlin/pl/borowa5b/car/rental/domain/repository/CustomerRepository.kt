package pl.borowa5b.car.rental.domain.repository

import pl.borowa5b.car.rental.domain.model.vo.CustomerId

interface CustomerRepository {

    fun exists(customerId: CustomerId): Boolean
}