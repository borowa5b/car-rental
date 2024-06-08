package pl.borowa5b.car.rental.infrastructure.generator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.vo.ApplicationEventId

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