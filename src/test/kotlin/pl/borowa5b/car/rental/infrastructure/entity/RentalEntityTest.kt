package pl.borowa5b.car.rental.infrastructure.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.DomainObjects.rental
import pl.borowa5b.car.rental.infrastructure.entity.EntityObjects.rentalEntity

class RentalEntityTest {

    @Test
    fun `should convert from domain object`() {
        // given
        val rental = rental()

        // when
        val result = RentalEntity.fromDomain(rental)

        // then
        assertThat(result.id).isEqualTo(rental.id.value)
        assertThat(result.carId).isEqualTo(rental.carId.value)
        assertThat(result.customerId).isEqualTo(rental.customerId.value)
        assertThat(result.startDate).isEqualTo(rental.startDate)
        assertThat(result.endDate).isEqualTo(rental.endDate)
        assertThat(result.price).isEqualTo(rental.price)
        assertThat(result.entityVersion).isEqualTo(rental.version)
        assertThat(result.creationDate).isNotNull()
        assertThat(result.modificationDate).isNotNull()
    }

    @Test
    fun `should convert to domain object`() {
        // given
        val rentalEntity = rentalEntity()

        // when
        val result = rentalEntity.toDomain()

        // then
        assertThat(result.id.value).isEqualTo(rentalEntity.id)
        assertThat(result.carId.value).isEqualTo(rentalEntity.carId)
        assertThat(result.customerId.value).isEqualTo(rentalEntity.customerId)
        assertThat(result.startDate).isEqualTo(rentalEntity.startDate)
        assertThat(result.endDate).isEqualTo(rentalEntity.endDate)
        assertThat(result.price).isEqualTo(rentalEntity.price)
        assertThat(result.version).isEqualTo(rentalEntity.entityVersion)
    }
}