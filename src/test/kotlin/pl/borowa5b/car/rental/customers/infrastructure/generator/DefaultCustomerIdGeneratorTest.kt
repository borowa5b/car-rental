package pl.borowa5b.car.rental.customers.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.shared.infrastructure.generator.DefaultIdGenerator

class DefaultCustomerIdGeneratorTest {

    @Test
    fun `should generate customer id`() {
        // given
        val customerIdGenerator = DefaultCustomerIdGenerator(DefaultIdGenerator())

        // when
        val result = customerIdGenerator.generate()

        // then
        assertThat(result).isExactlyInstanceOf(CustomerId::class.java)
        assertThat(result.value).startsWith(CustomerId.PREFIX)
    }
}