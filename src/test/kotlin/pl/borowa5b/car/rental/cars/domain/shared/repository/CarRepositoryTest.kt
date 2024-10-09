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
        carRepository.save(car)

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
    fun `should check if car exists by brand, model, generation, productionYear and color`(
        brand: Brand,
        model: String,
        generation: String,
        productionYear: Int,
        color: String,
        expectedResult: Boolean
    ) {
        // given
        val car = car(brand = Brand.TOYOTA, model = "Corolla", generation = "1", productionYear = 1997, color = "black")
        carRepository.save(car)

        // when
        val result = carRepository.existsBy(brand, model, generation, productionYear, color)

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

    @Test
    fun `should find car by id`() {
        // given
        val car = car()
        carRepository.save(car)

        // when
        val result = carRepository.findBy(car.id)

        // then
        assertThat(result).isEqualTo(car)
    }

    private class TestCarRepository(private val cars: ArrayList<Car> = ArrayList()) : CarRepository {

        override fun existsBy(carId: CarId): Boolean = cars.any { it.id == carId }

        override fun existsBy(
            brand: Brand,
            model: String,
            generation: String,
            productionYear: Int,
            color: String
        ): Boolean =
            cars.any { it.brand == brand && it.model == model && it.productionYear == productionYear && it.generation == generation && it.color == color }

        override fun save(car: Car): Car {
            cars.add(car)
            return car
        }

        override fun findBy(carId: CarId): Car? = cars.firstOrNull { it.id == carId }

        fun deleteAll() = cars.clear()
    }
}