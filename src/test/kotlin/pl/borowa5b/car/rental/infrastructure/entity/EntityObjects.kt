package pl.borowa5b.car.rental.infrastructure.entity

import pl.borowa5b.car.rental.domain.model.DomainObjects.carId
import pl.borowa5b.car.rental.domain.model.DomainObjects.customerId
import pl.borowa5b.car.rental.domain.model.DomainObjects.rentalId
import pl.borowa5b.car.rental.domain.model.RentalStatus
import java.math.BigDecimal
import java.time.OffsetDateTime

object EntityObjects {

    fun rentalEntity(
        id: String = rentalId().value,
        carId: String = carId().value,
        customerId: String = customerId().value,
        status: RentalStatus = RentalStatus.IN_PROGRESS,
        price: BigDecimal = BigDecimal.TEN,
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z"),
    ): RentalEntity = RentalEntity(
        id = id,
        carId = carId,
        customerId = customerId,
        status = status,
        price = price,
        startDate = startDate,
        endDate = endDate,
        entityVersion = 0
    )

    fun carEntity(
        id: String = carId().value,
        brand: String = "Toyota",
        model: String = "Corolla",
        generation: String = "LE",
        year: Int = 2022,
        color: String = "Blue",
        pricePerDay: Int = 200,
        quantity: Int = 5,
    ): CarEntity = CarEntity(
        id = id,
        brand = brand,
        model = model,
        generation = generation,
        year = year,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity,
        entityVersion = 0
    )

    fun customerEntity(
        id: String = customerId().value,
        name: String = "John",
        surname: String = "Doe",
        email: String = "john.doe@example.com",
        phoneNumber: String = "+48123123123",
        address: String = "Warsaw",
        documentNumber: String = "ABC123123123",
    ): CustomerEntity = CustomerEntity(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phoneNumber = phoneNumber,
        address = address,
        documentNumber = documentNumber,
        entityVersion = 0
    )
}