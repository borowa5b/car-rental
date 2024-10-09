package pl.borowa5b.car.rental.cars.application.filter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.application.filter.FilterObjects.getCarsFilter
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import java.math.BigDecimal

class GetCarsFilterTest {

    @Test
    fun `should validate filter`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val getCarsFilter = getCarsFilter(
            id = "CAR1214242443242",
            brand = "FORD",
            model = "Mondeo",
            generation = "MK3",
            productionYear = 2006,
            color = "Metallic Black",
            pricePerDayFrom = BigDecimal.ONE,
            pricePerDayTo = BigDecimal.TEN,
            quantityFrom = 10,
            quantityTo = 20
        )

        // when
        getCarsFilter.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isFalse()
    }

    @Test
    fun `should not validate filter`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val getCarsFilter = getCarsFilter(
            id = "wrongId",
            brand = "wrongBrand",
            model = "model",
            generation = "generation",
            productionYear = 1800,
            color = "color",
            pricePerDayFrom = BigDecimal.ZERO,
            pricePerDayTo = BigDecimal.ZERO,
            quantityFrom = -5,
            quantityTo = -5
        )

        // when
        getCarsFilter.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(7)
    }

    @Test
    fun `should convert to query`() {
        // given
        val carId = "CAR1214242443242"
        val brand = "FORD"
        val model = "mondeo"
        val generation = "mk3"
        val productionYear = 2006
        val color = "metallic black"
        val pricePerDayFrom = BigDecimal.ONE
        val pricePerDayTo = BigDecimal.TEN
        val quantityFrom = 10
        val quantityTo = 20
        val getCarsFilter = getCarsFilter(
            id = carId,
            brand = brand,
            model = model,
            generation = generation,
            productionYear = productionYear,
            color = color,
            pricePerDayFrom = pricePerDayFrom,
            pricePerDayTo = pricePerDayTo,
            quantityFrom = quantityFrom,
            quantityTo = quantityTo
        )

        // when
        val result = getCarsFilter.toQuery()

        // then
        assertThat(result.id).isEqualTo(CarId(carId))
        assertThat(result.brand?.name).isEqualTo(brand)
        assertThat(result.model).isEqualTo(model)
        assertThat(result.generation).isEqualTo(generation)
        assertThat(result.productionYear).isEqualTo(productionYear)
        assertThat(result.color).isEqualTo(color)
        assertThat(result.pricePerDayFrom).isEqualTo(pricePerDayFrom)
        assertThat(result.pricePerDayTo).isEqualTo(pricePerDayTo)
        assertThat(result.quantityFrom).isEqualTo(quantityFrom)
        assertThat(result.quantityTo).isEqualTo(quantityTo)
    }
}