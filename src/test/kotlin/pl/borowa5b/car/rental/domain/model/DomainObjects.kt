package pl.borowa5b.car.rental.domain.model

import java.math.BigDecimal
import java.time.OffsetDateTime
import kotlin.random.Random

object DomainObjects {

    fun rental(
        id: RentalId = rentalId(),
        carId: CarId = carId(),
        customerId: CustomerId = customerId(),
        status: RentalStatus = RentalStatus.IN_PROGRESS,
        price: BigDecimal = BigDecimal.TEN,
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z"),
    ): Rental = Rental(
        id = id,
        carId = carId,
        customerId = customerId,
        status = status,
        price = price,
        startDate = startDate,
        endDate = endDate,
        version = 0
    )

    fun car(
        id: CarId = carId(),
        brand: String = "Toyota",
        model: String = "Corolla",
        generation: String = "LE",
        year: Int = 2022,
        color: String = "Blue",
        pricePerDay: Int = 200,
        quantity: Int = 5,
    ): Car = Car(
        id = id,
        brand = brand,
        model = model,
        generation = generation,
        year = year,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity,
        version = 0
    )

    fun customer(
        id: CustomerId = customerId(),
        name: String = "John",
        surname: String = "Doe",
        email: String = "john.doe@example.com",
        phoneNumber: String = "+48123123123",
        address: String = "Warsaw",
        documentNumber: String = "ABC123123123",
    ): Customer = Customer(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phoneNumber = phoneNumber,
        address = address,
        documentNumber = documentNumber,
        version = 0
    )

    fun rentalId(): RentalId = RentalId("${RentalId.PREFIX}${Random.nextInt(1000000, 9999999)}")

    fun carId(): CarId = CarId("${CarId.PREFIX}${Random.nextInt(1000000, 9999999)}")

    fun customerId(): CustomerId = CustomerId("${CustomerId.PREFIX}${Random.nextInt(1000000, 9999999)}")

}