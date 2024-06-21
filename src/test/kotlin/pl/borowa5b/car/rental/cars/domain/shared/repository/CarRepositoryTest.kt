package pl.borowa5b.car.rental.cars.domain.shared.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand

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
        val result = carRepository.existsBy(CarId(carId))

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    @ParameterizedTest
    @CsvSource(
        "TOYOTA, Corolla, 1, 1997, black, true",
        "FORD, Mondeo, MK3, 2006, blue, false",
    )
    fun `should check if car exists by brand, model, generation, year and color`(
        brand: Brand,
        model: String,
        generation: String,
        year: Int,
        color: String,
        expectedResult: Boolean
    ) {
        // given
        val car = car(brand = Brand.TOYOTA, model = "Corolla", generation = "1", year = 1997, color = "black")
        (carRepository as TestCarRepository).save(car)

        // when
        val result = carRepository.existsBy(brand, model, generation, year, color)

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

        override fun existsBy(carId: CarId): Boolean = cars.any { it.id == carId }

        override fun existsBy(brand: Brand, model: String, generation: String, year: Int, color: String): Boolean =
            cars.any { it.brand == brand && it.model == model && it.year == year && it.generation == generation && it.color == color }

        override fun save(car: Car): Car {
            cars.add(car)
            return car
        }

        fun deleteAll() = cars.clear()
    }
}