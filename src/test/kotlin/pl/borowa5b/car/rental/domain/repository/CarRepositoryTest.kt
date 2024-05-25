package pl.borowa5b.car.rental.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.borowa5b.car.rental.domain.model.Car
import pl.borowa5b.car.rental.domain.model.CarId
import pl.borowa5b.car.rental.domain.model.DomainObjects.car
import pl.borowa5b.car.rental.infrastructure.entity.CarEntity

class CarRepositoryTest {

    @ParameterizedTest
    @CsvSource(
        "CAR1, true",
        "CAR2, false"
    )
    fun `should check if car exists`(carId: String, expectedResult: Boolean) {
        // given
        val carRepository = TestCarRepository()
        val car = car(id = CarId("CAR1"))
        carRepository.save(car)

        // when
        val result = carRepository.exists(CarId(carId))

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    private class TestCarRepository(private val cars: ArrayList<CarEntity> = ArrayList()) : CarRepository {

        override fun exists(carId: CarId): Boolean = cars.any { it.id == carId.value }

        fun save(car: Car) = cars.add(CarEntity.fromDomain(car))
    }
}