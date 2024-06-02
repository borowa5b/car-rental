package pl.borowa5b.car.rental.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.borowa5b.car.rental.domain.model.DomainObjects.rental
import pl.borowa5b.car.rental.domain.model.Rental
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import pl.borowa5b.car.rental.domain.model.vo.RentalId
import pl.borowa5b.car.rental.domain.model.vo.RentalStatus
import pl.borowa5b.car.rental.infrastructure.entity.RentalEntity

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
        assertThat(result.id).isEqualTo(rental.id.value)
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
        val rental = rental(customerId = CustomerId("CTR1"), status = RentalStatus.IN_PROGRESS)
        rentalRepository.save(rental)

        // when
        val result = rentalRepository.hasActiveRentals(CustomerId(customerId))

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    private class TestRentalRepository(private val rentals: ArrayList<RentalEntity> = ArrayList()) : RentalRepository {

        override fun save(rental: Rental): RentalEntity {
            val rentalEntity = RentalEntity.fromDomain(rental)
            rentals.add(rentalEntity)
            return rentalEntity
        }

        override fun findById(id: RentalId): Rental? = rentals.find { it.id == id.value }?.toDomain()

        override fun hasActiveRentals(customerId: CustomerId): Boolean =
            rentals.any { it.customerId == customerId.value && it.status != RentalStatus.ENDED }

        fun deleteAll() = rentals.clear()
    }
}