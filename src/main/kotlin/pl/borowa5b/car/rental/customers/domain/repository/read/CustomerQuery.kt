package pl.borowa5b.car.rental.customers.domain.repository.read

import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId

data class CustomerQuery(
    val id: CustomerId?,
    val name: String?,
    val surname: String?,
    val email: String?,
    val phoneNumber: String?,
)