package pl.borowa5b.car.rental.domain.repository

import pl.borowa5b.car.rental.domain.model.Customer
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import pl.borowa5b.car.rental.infrastructure.entity.CustomerEntity

interface CustomerRepository {

    fun exists(customerId: CustomerId): Boolean

    fun save(customer: Customer): CustomerEntity

    fun findById(id: CustomerId): Customer?
}