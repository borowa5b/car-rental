package pl.borowa5b.car.rental.events.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventId
import pl.borowa5b.car.rental.shared.infrastructure.generator.DefaultIdGenerator

class DefaultApplicationEventIdGeneratorTest {

    @Test
    fun `should generate application event id`() {
        // given
        val applicationEventIdGenerator = DefaultApplicationEventIdGenerator(DefaultIdGenerator())

        // when
        val result = applicationEventIdGenerator.generate()

        // then
        assertThat(result).isExactlyInstanceOf(ApplicationEventId::class.java)
        assertThat(result.value).startsWith(ApplicationEventId.PREFIX)
    }
}