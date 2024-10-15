package pl.borowa5b.car.rental.cars.infrastructure.repository.read

import org.assertj.core.api.Assertions.assertThat
import org.jooq.impl.DSL.field
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.repository.read.ReadObjects.carQuery
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.cars.infrastructure.repository.read.CarTableDefinition.Column
import java.math.BigDecimal

class CarConditionsBuilderTest {

    @Test
    fun `should build car id condition`() {
        // given
        val query = carQuery(id = carId())
        val expectedCondition = field(Column.ID).eq(query.id?.value)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build brand condition`() {
        // given
        val query = carQuery(brand = Brand.TESLA)
        val expectedCondition = field(Column.BRAND).eq(query.brand?.name)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build model condition`() {
        // given
        val query = carQuery(model = "Model S")
        val expectedCondition = field(Column.MODEL).eq(query.model)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build generation condition`() {
        // given
        val query = carQuery(generation = "SE")
        val expectedCondition = field(Column.GENERATION).eq(query.generation)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build production year condition`() {
        // given
        val query = carQuery(productionYear = 2020)
        val expectedCondition = field(Column.PRODUCTION_YEAR).eq(query.productionYear)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build color condition`() {
        // given
        val query = carQuery(color = "Red")
        val expectedCondition = field(Column.COLOR).eq(query.color)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build price per day from condition`() {
        // given
        val query = carQuery(pricePerDayFrom = BigDecimal.TEN)
        val expectedCondition = field(Column.PRICE_PER_DAY).greaterOrEqual(query.pricePerDayFrom)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build price per day to condition`() {
        // given
        val query = carQuery(pricePerDayTo = BigDecimal.TEN)
        val expectedCondition = field(Column.PRICE_PER_DAY).lessOrEqual(query.pricePerDayTo)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build quantity from condition`() {
        // given
        val query = carQuery(quantityFrom = 10)
        val expectedCondition = field(Column.QUANTITY).greaterOrEqual(query.quantityFrom)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build quantity to condition`() {
        // given
        val query = carQuery(quantityTo = 10)
        val expectedCondition = field(Column.QUANTITY).lessOrEqual(query.quantityTo)

        // when
        val result = CarConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }
}