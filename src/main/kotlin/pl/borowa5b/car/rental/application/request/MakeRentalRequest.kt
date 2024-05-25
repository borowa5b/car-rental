package pl.borowa5b.car.rental.application.request

import org.springframework.util.Assert.state
import pl.borowa5b.car.rental.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.domain.model.CarId
import pl.borowa5b.car.rental.domain.model.CustomerId
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class MakeRentalRequest(
    val carId: String?,
    val customerId: String?,
    val startDate: String?,
    val endDate: String?
) {

    fun validate() {
        carId?.let {
            state(it.isNotBlank()) { "Parameter `carId` must not be empty" }
        }
        customerId?.let {
            state(it.isNotBlank()) { "Parameter `customerId` must not be empty" }
        }
        startDate?.let {
            state(it.isNotBlank()) { "Parameter `startDate` must not be empty" }

            val startDateParsed = OffsetDateTime.parse(it)
            state(
                startDateParsed.isAfter(OffsetDateTime.now(ZoneOffset.UTC))
            ) { "Parameter `startDate` must contain date time from future" }

            endDate?.let {
                state(endDate.isNotBlank()) { "Parameter `endDate` must not be empty" }

                val endDateParsed = OffsetDateTime.parse(endDate)
                state(
                    endDateParsed.isAfter(startDateParsed)
                ) { "Parameter `endDate` must contain date time after `startDate`" }
            }
        }
    }

    fun toCommand(): MakeRentalCommand = MakeRentalCommand(
        CarId(carId!!),
        CustomerId(customerId!!),
        startDate = OffsetDateTime.parse(startDate!!),
        endDate = OffsetDateTime.parse(endDate!!)
    )
}