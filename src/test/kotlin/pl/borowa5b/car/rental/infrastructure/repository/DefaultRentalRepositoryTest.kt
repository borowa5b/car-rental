package pl.borowa5b.car.rental.infrastructure.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.DomainObjects.rental
import pl.borowa5b.car.rental.domain.model.Rental
import pl.borowa5b.car.rental.domain.model.RentalId
import pl.borowa5b.car.rental.domain.repository.RentalRepository
import pl.borowa5b.car.rental.infrastructure.entity.RentalEntity

class DefaultRentalRepositoryTest {

    @Test
    fun `should save rental`() {
        // given
        val rentalRepository = TestRentalRepository()
        val rental = rental()

        // when
        val result = rentalRepository.save(rental)

        // then
        assertThat(result.id).isEqualTo(rental.id.value)
    }

    @Test
    fun `should find rental by id`() {
        // given
        val rentalRepository = TestRentalRepository()
        val rental = rental()
        rentalRepository.save(rental)

        // when
        val result = rentalRepository.findById(rental.id)

        // then
        assertThat(result).isEqualTo(rental)
    }
}

class TestRentalRepository(private val rentals: ArrayList<RentalEntity> = ArrayList()) : RentalRepository {

    override fun save(rental: Rental): RentalEntity {
        val rentalEntity = RentalEntity.fromDomain(rental)
        rentals.add(rentalEntity)
        return rentalEntity
    }

    override fun findById(id: RentalId): Rental? = rentals.find { it.id == id.value }?.toDomain()
}