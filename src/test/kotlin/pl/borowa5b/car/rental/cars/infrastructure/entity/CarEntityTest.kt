package pl.borowa5b.car.rental.cars.infrastructure.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car
import pl.borowa5b.car.rental.cars.infrastructure.entity.EntityObjects.carEntity

class CarEntityTest {

    @Test
    fun `should convert from domain object`() {
        // given
        val car = car()

        // when
        val result = CarEntity.fromDomain(car)

        // then
        assertThat(result.id).isEqualTo(car.id.value)
        assertThat(result.brand).isEqualTo(car.brand)
        assertThat(result.model).isEqualTo(car.model)
        assertThat(result.generation).isEqualTo(car.generation)
        assertThat(result.year).isEqualTo(car.year)
        assertThat(result.color).isEqualTo(car.color)
        assertThat(result.pricePerDay).isEqualTo(car.pricePerDay)
        assertThat(result.quantity).isEqualTo(car.quantity)
        assertThat(result.entityVersion).isEqualTo(car.version)
        assertThat(result.creationDate).isNotNull()
        assertThat(result.modificationDate).isNotNull()
    }

    @Test
    fun `should convert to domain object`() {
        // given
        val carEntity = carEntity()

        // when
        val result = carEntity.toDomain()

        // then
        assertThat(result.id.value).isEqualTo(carEntity.id)
        assertThat(result.brand).isEqualTo(carEntity.brand)
        assertThat(result.model).isEqualTo(carEntity.model)
        assertThat(result.generation).isEqualTo(carEntity.generation)
        assertThat(result.year).isEqualTo(carEntity.year)
        assertThat(result.color).isEqualTo(carEntity.color)
        assertThat(result.pricePerDay).isEqualTo(carEntity.pricePerDay)
        assertThat(result.quantity).isEqualTo(carEntity.quantity)
        assertThat(result.version).isEqualTo(carEntity.entityVersion)
    }
}