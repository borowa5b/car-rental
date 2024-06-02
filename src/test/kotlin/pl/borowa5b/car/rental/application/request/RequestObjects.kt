package pl.borowa5b.car.rental.application.request

import pl.borowa5b.car.rental.application.filter.GetRentalsFilter
import pl.borowa5b.car.rental.domain.model.DomainObjects.carId
import pl.borowa5b.car.rental.domain.model.DomainObjects.customerId
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object RequestObjects {

    fun calculateRentalRequest(
        startDate: String? = OffsetDateTime.now(ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.MICROS)
            .plusDays(1)
            .format(DateTimeFormatter.ISO_DATE_TIME),
        endDate: String? = OffsetDateTime.now(ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.MICROS)
            .plusDays(2)
            .format(DateTimeFormatter.ISO_DATE_TIME)
    ): CalculateRentalRequest = CalculateRentalRequest(
        startDate = startDate,
        endDate = endDate
    )

    fun makeRentalRequest(
        carId: String? = carId().value,
        customerId: String? = customerId().value,
        startDate: String? = OffsetDateTime.now(ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.MICROS)
            .plusDays(1)
            .format(DateTimeFormatter.ISO_DATE_TIME),
        endDate: String? = OffsetDateTime.now(ZoneOffset.UTC)
            .truncatedTo(ChronoUnit.MICROS)
            .plusDays(2)
            .format(DateTimeFormatter.ISO_DATE_TIME)
    ): MakeRentalRequest = MakeRentalRequest(
        carId = carId,
        customerId = customerId,
        startDate = startDate,
        endDate = endDate,
    )

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

    fun pageRequest(number: Int? = 1, size: Int? = 10): PageRequest = PageRequest(
        number = number,
        size = size
    )
}