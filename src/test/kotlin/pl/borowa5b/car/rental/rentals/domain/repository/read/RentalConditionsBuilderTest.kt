package pl.borowa5b.car.rental.rentals.domain.repository.read

import org.assertj.core.api.Assertions.assertThat
import org.jooq.impl.DSL.field
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.rentals.domain.model.DomainObjects.rentalQuery
import pl.borowa5b.car.rental.rentals.infrastructure.repository.read.RentalTableDefinition.Column
import java.time.OffsetDateTime
import java.time.ZoneOffset

class RentalConditionsBuilderTest {

    @Test
    fun `should build car id rental condition`() {
        // given
        val query = rentalQuery(carId = carId())
        val expectedCondition = field(Column.CAR_ID).eq(query.carId?.value)

        // when
        val result = RentalConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build customer id rental condition`() {
        // given
        val query = rentalQuery(customerId = customerId())
        val expectedCondition = field(Column.CUSTOMER_ID).eq(query.customerId?.value)

        // when
        val result = RentalConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build start date from rental condition`() {
        // given
        val query = rentalQuery(startDateFrom = OffsetDateTime.now(ZoneOffset.UTC))
        val expectedCondition = field(Column.START_DATE).greaterOrEqual(query.startDateFrom)

        // when
        val result = RentalConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build start date to rental condition`() {
        // given
        val query = rentalQuery(startDateTo = OffsetDateTime.now(ZoneOffset.UTC))
        val expectedCondition = field(Column.START_DATE).lessOrEqual(query.startDateTo)

        // when
        val result = RentalConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build end date from rental condition`() {
        // given
        val query = rentalQuery(endDateFrom = OffsetDateTime.now(ZoneOffset.UTC))
        val expectedCondition = field(Column.END_DATE).greaterOrEqual(query.endDateFrom)

        // when
        val result = RentalConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build end date to rental condition`() {
        // given
        val query = rentalQuery(endDateTo = OffsetDateTime.now(ZoneOffset.UTC))
        val expectedCondition = field(Column.END_DATE).lessOrEqual(query.endDateTo)

        // when
        val result = RentalConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }
}