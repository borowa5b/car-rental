package pl.borowa5b.car.rental.domain.command

import pl.borowa5b.car.rental.domain.model.DomainObjects.customerId
import pl.borowa5b.car.rental.domain.model.vo.CarId
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import java.time.OffsetDateTime

object CommandObjects {

    fun calculateRentalCommand(
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
    ): CalculateRentalCommand = CalculateRentalCommand(
        startDate = startDate,
        endDate = endDate
    )

    fun makeRentalCommand(
        carId: CarId,
        customerId: CustomerId,
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
    ): MakeRentalCommand = MakeRentalCommand(
        carId = carId,
        customerId = customerId,
        startDate = startDate,
        endDate = endDate
    )

    fun saveCustomerCommand(
        id: CustomerId = customerId(),
        name: String = "Joe",
        surname: String = "Doe",
        email: String = "joe.doe@email.com",
        phone: String = "952123531",
        address: String = "Kwiatkowa 9, 00-000 Warszawa",
        documentNumber: String = "FAE124532"
    ): SaveCustomerCommand = SaveCustomerCommand(
        id = id.value,
        name = name,
        surname = surname,
        email = email,
        phone = phone,
        address = address,
        documentNumber = documentNumber
    )
}