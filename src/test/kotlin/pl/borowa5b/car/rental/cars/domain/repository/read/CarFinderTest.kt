package pl.borowa5b.car.rental.cars.domain.repository.read

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.repository.read.ReadObjects.carDetails
import pl.borowa5b.car.rental.cars.domain.repository.read.ReadObjects.carQuery
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable
import pl.borowa5b.car.rental.shared.domain.pagination.PaginationObjects.page

class CarFinderTest {

    private val carFinder: CarFinder = TestCarFinder()

    @Test
    fun `should find cars by query`() {
        // given
        val carDetails1 = carDetails(brand = Brand.CADILLAC)
        val carDetails2 = carDetails(brand = Brand.CADILLAC)
        val carDetails3 = carDetails(brand = Brand.INFINITI)
        (carFinder as TestCarFinder).save(carDetails1, carDetails2, carDetails3)
        val query = carQuery(brand = Brand.CADILLAC)

        // when
        val result = carFinder.findBy(query, page())

        // then
        val data = result.data
        assertThat(data).hasSize(2)
        assertThat(data[0]).isEqualTo(carDetails1)
        assertThat(data[1]).isEqualTo(carDetails2)
    }

    @Test
    fun `should find car by id`() {
        // given
        val carId = CarId("CAR123")
        val carDetails = carDetails(id = carId)
        (carFinder as TestCarFinder).save(carDetails)

        // when
        val result = carFinder.findBy(carId)

        // then
        assertThat(result).isEqualTo(carDetails)
    }

    private class TestCarFinder(private val cars: ArrayList<CarDetails> = ArrayList()) : CarFinder {

        override fun findBy(query: CarQuery, page: Page): Pageable<CarDetails> =
            Pageable.of(cars.filter { car ->
                var condition = true
                query.id?.let {
                    condition = condition && it.value == car.id
                }
                query.brand?.let {
                    condition = condition && it.name == car.brand
                }
                query.model?.let {
                    condition = condition && it == car.model
                }
                query.generation?.let {
                    condition = condition && it == car.generation
                }
                query.productionYear?.let {
                    condition = condition && car.productionYear == it
                }
                query.color?.let {
                    condition = condition && it == car.color
                }
                query.pricePerDayFrom?.let {
                    condition = condition && car.pricePerDay >= it
                }
                query.pricePerDayTo?.let {
                    condition = condition && car.pricePerDay <= it
                }
                query.quantityFrom?.let {
                    condition = condition && car.quantity >= it
                }
                query.quantityTo?.let {
                    condition = condition && car.quantity <= it
                }
                condition
            }.toList(), page)

        override fun findBy(carId: CarId): CarDetails = cars.first { it.id == carId.value }

        fun save(vararg car: CarDetails) = car.forEach { cars.add(it) }
    }
}