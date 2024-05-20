package pl.borowa5b.car.rental.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.CarId

class DefaultCarIdGeneratorTest {

    @Test
    fun `should generate car id`() {
        // given
        val carIdGenerator = DefaultCarIdGenerator(DefaultIdGenerator())

        // when
        val result = carIdGenerator.generate()

        // then
        assertThat(result).isExactlyInstanceOf(CarId::class.java)
        assertThat(result.value).startsWith(CarId.PREFIX)
    }
}