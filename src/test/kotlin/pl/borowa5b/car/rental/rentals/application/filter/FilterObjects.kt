package pl.borowa5b.car.rental.rentals.application.filter

object FilterObjects {

    fun getRentalsFilter(
        carId: String? = null,
        customerId: String? = null,
        startDateFrom: String? = null,
        startDateTo: String? = null,
        endDateFrom: String? = null,
        endDateTo: String? = null
    ): GetRentalsFilter = GetRentalsFilter(
        carId = carId,
        customerId = customerId,
        startDateFrom = startDateFrom,
        startDateTo = startDateTo,
        endDateFrom = endDateFrom,
        endDateTo = endDateTo
    )
}