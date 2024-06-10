package pl.borowa5b.car.rental.rentals.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.shared.infrastructure.generator.DefaultIdGenerator

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