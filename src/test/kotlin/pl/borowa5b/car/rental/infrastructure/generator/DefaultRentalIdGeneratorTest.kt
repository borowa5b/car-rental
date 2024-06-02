package pl.borowa5b.car.rental.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.vo.RentalId

class DefaultRentalIdGeneratorTest {

    @Test
    fun `should generate rental id`() {
        // given
        val rentalIdGenerator = DefaultRentalIdGenerator(DefaultIdGenerator())

        // when
        val result = rentalIdGenerator.generate()

        // then
        assertThat(result).isInstanceOf(RentalId::class.java)
        assertThat(result.value).startsWith(RentalId.PREFIX)
    }
}