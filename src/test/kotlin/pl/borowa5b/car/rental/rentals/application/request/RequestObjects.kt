package pl.borowa5b.car.rental.rentals.application.request

import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
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
}