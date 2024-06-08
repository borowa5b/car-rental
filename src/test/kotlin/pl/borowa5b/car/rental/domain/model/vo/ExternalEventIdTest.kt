package pl.borowa5b.car.rental.domain.model.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException

class ExternalEventIdTest {

    @Test
    fun `should validate application event id`() {
        // given

        // when
        val result = ExternalEventId("EXT123")

        // then
        assertThat(result).isNotNull()
    }

    @Test
    fun `should not validate car id`() {
        // given

        // when
        val result = catchThrowable { ExternalEventId("123") }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }
}