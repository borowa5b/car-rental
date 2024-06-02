package pl.borowa5b.car.rental.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.vo.CustomerId

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