package pl.borowa5b.car.rental.events.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.events.domain.vo.EventId
import pl.borowa5b.car.rental.shared.infrastructure.generator.DefaultIdGenerator

class DefaultEventIdGeneratorTest {

    @Test
    fun `should generate application event id`() {
        // given
        val applicationEventIdGenerator = DefaultEventIdGenerator(DefaultIdGenerator())

        // when
        val result = applicationEventIdGenerator.generate()

        // then
        assertThat(result).isExactlyInstanceOf(EventId::class.java)
        assertThat(result.value).startsWith(EventId.PREFIX)
    }
}