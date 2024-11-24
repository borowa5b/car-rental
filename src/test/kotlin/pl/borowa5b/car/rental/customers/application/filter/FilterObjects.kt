package pl.borowa5b.car.rental.customers.application.filter

object FilterObjects {

    fun getCustomersFilter(
        id: String? = null,
        name: String? = null,
        surname: String? = null,
        email: String? = null,
        phoneNumber: String? = null
    ): GetCustomersFilter = GetCustomersFilter(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phoneNumber = phoneNumber
    )
}