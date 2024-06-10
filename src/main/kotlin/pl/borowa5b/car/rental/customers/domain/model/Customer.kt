package pl.borowa5b.car.rental.customers.domain.model

import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId

data class Customer(
    val id: CustomerId,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val documentNumber: String,
    val version: Long = 0
)