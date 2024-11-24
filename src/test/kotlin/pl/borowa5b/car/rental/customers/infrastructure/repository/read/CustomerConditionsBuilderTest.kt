package pl.borowa5b.car.rental.customers.infrastructure.repository.read

import org.assertj.core.api.Assertions.assertThat
import org.jooq.impl.DSL.field
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.customers.domain.repository.read.ReadObjects.customerQuery
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.customers.infrastructure.repository.read.CustomerTableDefinition.Column

class CustomerConditionsBuilderTest {

    @Test
    fun `should build customer id condition`() {
        // given
        val query = customerQuery(id = customerId())
        val expectedCondition = field(Column.ID).eq(query.id?.value)

        // when
        val result = CustomerConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build name condition`() {
        // given
        val query = customerQuery(name = "John")
        val expectedCondition = field(Column.NAME).eq(query.name)

        // when
        val result = CustomerConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build surname condition`() {
        // given
        val query = customerQuery(surname = "Doe")
        val expectedCondition = field(Column.SURNAME).eq(query.surname)

        // when
        val result = CustomerConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build email condition`() {
        // given
        val query = customerQuery(email = "john.doe@example.com")
        val expectedCondition = field(Column.EMAIL).eq(query.email)

        // when
        val result = CustomerConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }

    @Test
    fun `should build phone number condition`() {
        // given
        val query = customerQuery(phoneNumber = "123456789")
        val expectedCondition = field(Column.PHONE_NUMBER).eq(query.phoneNumber)

        // when
        val result = CustomerConditionsBuilder.build(query)

        // then
        assertThat(result).isEqualTo(expectedCondition)
    }
}