package pl.borowa5b.car.rental.rentals.domain.shared.repository

import  org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.model.DomainObjects.rental
import pl.borowa5b.car.rental.rentals.domain.model.Rental
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.time.OffsetDateTime
import java.time.ZoneOffset

class RentalRepositoryTest {

    private val rentalRepository: RentalRepository = TestRentalRepository()

    @BeforeEach
    fun `before each`() {
        (rentalRepository as TestRentalRepository).deleteAll()
    }

    @Test
    fun `should save rental`() {
        // given
        val rental = rental()

        // when
        val result = rentalRepository.save(rental)

        // then
        assertThat(result.id).isEqualTo(rental.id)
    }

    @Test
    fun `should find rental by id`() {
        // given
        val rental = rental()
        rentalRepository.save(rental)

        // when
        val result = rentalRepository.findById(rental.id)

        // then
        assertThat(result).isEqualTo(rental)
    }

    @Test
    fun `should not find rental by id`() {
        // given
        val rentalId = RentalId("RNL123")

        // when
        val result = rentalRepository.findById(rentalId)

        // then
        assertThat(result).isNull()
    }

    @ParameterizedTest
    @CsvSource(
        "CTR1, true",
        "CTR2, false"
    )
    fun `should check if customer has rentals`(customerId: String, expectedResult: Boolean) {
        // given
        val rental = rental(customerId = CustomerId("CTR1"), status = RentalStatus.STARTED)
        rentalRepository.save(rental)

        // when
        val result = rentalRepository.hasActiveRentals(CustomerId(customerId))

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @CsvSource(
        "CAR1, true",
        "CAR2, false"
    )
    fun `should check if car has rentals`(carId: String, expectedResult: Boolean) {
        // given
        val rental = rental(carId = CarId("CAR1"), status = RentalStatus.STARTED)
        rentalRepository.save(rental)

        // when
        val result = rentalRepository.hasActiveRentals(CarId(carId))

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should find rental to start`() {
        // given
        val rental = rental(status = RentalStatus.NEW)
        rentalRepository.save(rental)

        // when
        val result = rentalRepository.findToStart(OffsetDateTime.now(ZoneOffset.UTC))

        // then
        assertThat(result).isEqualTo(rental.id)
    }

    @Test
    fun `should find rental to end`() {
        // given
        val rental = rental(status = RentalStatus.STARTED)
        rentalRepository.save(rental)

        // when
        val result = rentalRepository.findToEnd(OffsetDateTime.now(ZoneOffset.UTC))

        // then
        assertThat(result).isEqualTo(rental.id)
    }

    private class TestRentalRepository(private val rentals: ArrayList<Rental> = ArrayList()) : RentalRepository {

        override fun save(rental: Rental): Rental {
            rentals.add(rental)
            return rental
        }

        override fun findById(id: RentalId): Rental? = rentals.find { it.id == id }

        override fun hasActiveRentals(customerId: CustomerId): Boolean =
            rentals.any { it.customerId == customerId && it.status != RentalStatus.ENDED }

        override fun hasActiveRentals(carId: CarId): Boolean =
            rentals.any { it.carId == carId && it.status != RentalStatus.ENDED }

        override fun findToStart(currentDate: OffsetDateTime): RentalId? =
            rentals.firstOrNull { it.status == RentalStatus.NEW && currentDate >= it.startDate }?.id

        override fun findToEnd(currentDate: OffsetDateTime): RentalId? =
            rentals.firstOrNull { it.status == RentalStatus.STARTED && currentDate >= it.endDate }?.id

        fun deleteAll() = rentals.clear()
    }
}