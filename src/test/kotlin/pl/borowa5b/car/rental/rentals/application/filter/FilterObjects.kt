package pl.borowa5b.car.rental.rentals.application.filter

object FilterObjects {

    fun getRentalsFilter(
        id: String? = null,
        carId: String? = null,
        customerId: String? = null,
        status: String? = null,
        startDateFrom: String? = null,
        startDateTo: String? = null,
        endDateFrom: String? = null,
        endDateTo: String? = null
    ): GetRentalsFilter = GetRentalsFilter(
        id = id,
        carId = carId,
        customerId = customerId,
        status = status,
        startDateFrom = startDateFrom,
        startDateTo = startDateTo,
        endDateFrom = endDateFrom,
        endDateTo = endDateTo
    )
}