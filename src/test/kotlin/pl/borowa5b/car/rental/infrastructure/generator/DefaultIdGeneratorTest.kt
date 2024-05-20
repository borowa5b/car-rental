package pl.borowa5b.car.rental.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DefaultIdGeneratorTest {

    @Test
    fun `should generate id`() {
        // given
        val prefix = "prefix"
        val idGenerator = DefaultIdGenerator()

        // when
        val result = idGenerator.generate(prefix = prefix)

        // then
        assertThat(result).startsWith(prefix)
    }
}