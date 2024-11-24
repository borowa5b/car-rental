package pl.borowa5b.car.rental.customers.domain.repository.read

import java.time.OffsetDateTime

data class CustomerDetails(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val documentNumber: String,
    val creationDate: OffsetDateTime,
    val modificationDate: OffsetDateTime?,
    val entityVersion: Int
)
