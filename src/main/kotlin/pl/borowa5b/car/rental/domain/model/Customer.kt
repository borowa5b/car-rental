package pl.borowa5b.car.rental.domain.model

data class Customer(
    val id: CustomerId,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val documentNumber: String,
    val version: Long
)