package pl.borowa5b.car.rental.customers.application.filter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.customers.application.filter.FilterObjects.getCustomersFilter
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler

class GetCustomersFilterTest {

    @Test
    fun `should validate filter`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val filter = getCustomersFilter(
            id = "CTR323454355435",
            name = "John",
            surname = "Wicked",
            email = "john.wicked@example.com",
            phoneNumber = "+481234567890"
        )

        // when
        filter.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isFalse()
    }

    @Test
    fun `should not validate filter`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val filter = getCustomersFilter(
            id = "wrongId",
            name = "",
            surname = "",
            email = "",
            phoneNumber = ""
        )

        // when
        filter.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(4)
    }

    @Test
    fun `should convert to query`() {
        // given
        val filter = getCustomersFilter(
            id = "CTR323454355435",
            name = "John",
            surname = "Wicked",
            email = "john.wicked@example.com",
            phoneNumber = "+481234567890"
        )

        // when
        val query = filter.toQuery()

        // then
        assertThat(query.id).isEqualTo(CustomerId("CTR323454355435"))
        assertThat(query.name).isEqualTo("John")
        assertThat(query.surname).isEqualTo("Wicked")
        assertThat(query.email).isEqualTo("john.wicked@example.com")
        assertThat(query.phoneNumber).isEqualTo("+481234567890")
    }
}