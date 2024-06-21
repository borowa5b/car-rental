package pl.borowa5b.car.rental.events.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException

class ApplicationEventIdTest {

    @Test
    fun `should validate application event id`() {
        // given

        // when
        val result = ApplicationEventId("AEV123")

        // then
        assertThat(result).isNotNull()
    }

    @Test
    fun `should not validate car id`() {
        // given

        // when
        val result = catchThrowable { ApplicationEventId("123") }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }
}