package pl.borowa5b.car.rental.cars.domain.shared.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId

class CarRepositoryTest {

    private val carRepository: CarRepository = TestCarRepository()

    @BeforeEach
    fun `before each`() {
        (carRepository as TestCarRepository).deleteAll()
    }

    @ParameterizedTest
    @CsvSource(
        "CAR1, true",
        "CAR2, false"
    )
    fun `should check if car exists`(carId: String, expectedResult: Boolean) {
        // given
        val car = car(id = CarId("CAR1"))
        (carRepository as TestCarRepository).save(car)

        // when
        val result = carRepository.exists(CarId(carId))

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should save car`() {
        // given
        val car = car()

        // when
        val result = carRepository.save(car)

        // then
        assertThat(result.id).isEqualTo(car.id)
    }

    private class TestCarRepository(private val cars: ArrayList<Car> = ArrayList()) : CarRepository {

        override fun exists(carId: CarId): Boolean = cars.any { it.id == carId }

        override fun save(car: Car): Car {
            cars.add(car)
            return car
        }

        fun deleteAll() = cars.clear()
    }
}