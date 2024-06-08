package pl.borowa5b.car.rental.domain.model

import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import pl.borowa5b.car.rental.domain.model.vo.*
import pl.borowa5b.car.rental.domain.model.vo.pagination.Page
import pl.borowa5b.car.rental.domain.repository.read.RentalDetails
import pl.borowa5b.car.rental.domain.repository.read.RentalQuery
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
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
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
        quantity: Int = 5
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
        documentNumber: String = "ABC123123123"
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

    fun externalEventId(): ExternalEventId =
        ExternalEventId("${ExternalEventId.PREFIX}${Random.nextInt(1000000, 9999999)}")

    fun applicationEventId(): ApplicationEventId =
        ApplicationEventId("${ApplicationEventId.PREFIX}${Random.nextInt(1000000, 9999999)}")

    fun order(
        direction: Direction = Direction.DESC,
        property: String = "id"
    ): Sort.Order = Sort.Order(direction, property)

    fun page(
        number: Int = 1,
        size: Int = 10,
        order: Sort.Order = order()
    ): Page = Page(
        number = number,
        size = size,
        order = order
    )

    fun rentalQuery(
        carId: CarId? = null,
        customerId: CustomerId? = null,
        startDateFrom: OffsetDateTime? = null,
        startDateTo: OffsetDateTime? = null,
        endDateFrom: OffsetDateTime? = null,
        endDateTo: OffsetDateTime? = null,
    ): RentalQuery = RentalQuery(
        carId = carId,
        customerId = customerId,
        startDateFrom = startDateFrom,
        startDateTo = startDateTo,
        endDateFrom = endDateFrom,
        endDateTo = endDateTo
    )

    fun rentalDetails(
        id: RentalId = rentalId(),
        carId: CarId = carId(),
        customerId: CustomerId = customerId(),
        status: RentalStatus = RentalStatus.IN_PROGRESS,
        price: BigDecimal = BigDecimal.TEN,
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
    ): RentalDetails = RentalDetails(
        id = id.value,
        carId = carId.value,
        customerId = customerId.value,
        status = status.name,
        price = price,
        startDate = startDate,
        endDate = endDate,
        creationDate = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        modificationDate = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        entityVersion = 0
    )

    fun externalEvent(
        id: String = externalEventId().value,
        type: String = "CustomerCreated",
        version: String = "1.0",
        status: ExternalEventStatus = ExternalEventStatus.NEW,
        payload: String = "{}",
        errorMessage: String? = null,
        creationDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        processedOnDate: OffsetDateTime? = null
    ): ExternalEvent = ExternalEvent(
        id = ExternalEventId(id),
        type = type,
        version = version,
        status = status,
        payload = payload,
        errorMessage = errorMessage,
        creationDate = creationDate,
        processedOnDate = processedOnDate,
        entityVersion = 0
    )

    fun applicationEvent(
        id: String = applicationEventId().value,
        type: String = "RentalMade",
        version: String = "1.0",
        status: ApplicationEventStatus = ApplicationEventStatus.NEW,
        payload: String = "{}",
        publishedOnDate: OffsetDateTime? = null
    ): ApplicationEvent = ApplicationEvent(
        id = ApplicationEventId(id),
        type = type,
        version = version,
        status = status,
        payload = payload,
        publishedOnDate = publishedOnDate,
        entityVersion = 0
    )
}