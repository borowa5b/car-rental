package pl.borowa5b.car.rental.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EnumSource
import pl.borowa5b.car.rental.domain.model.DomainObjects.externalEvent
import pl.borowa5b.car.rental.domain.model.vo.ExternalEventStatus

class ExternalEventTest {

    @Test
    fun `should mark as processing`() {
        // given
        val externalEvent = externalEvent(status = ExternalEventStatus.NEW)

        // when
        externalEvent.markAsProcessing()

        // then
        assertThat(externalEvent.status).isEqualTo(ExternalEventStatus.PROCESSING)
        assertThat(externalEvent.processedOnDate).isNull()
    }

    @ParameterizedTest
    @EnumSource(value = ExternalEventStatus::class, names = ["NEW"], mode = EnumSource.Mode.EXCLUDE)
    fun `should not mark as processing`(status: ExternalEventStatus) {
        // given
        val externalEvent = externalEvent(status = status)

        // when
        val result = catchThrowable { externalEvent.markAsProcessing() }

        // then
        assertThat(result).isExactlyInstanceOf(IllegalStateException::class.java)
        assertThat(externalEvent.status).isEqualTo(status)
        assertThat(externalEvent.processedOnDate).isNull()
    }

    @Test
    fun `should mark as processed`() {
        // given
        val externalEvent = externalEvent(status = ExternalEventStatus.PROCESSING)

        // when
        externalEvent.markAsProcessed()

        // then
        assertThat(externalEvent.status).isEqualTo(ExternalEventStatus.PROCESSED)
        assertThat(externalEvent.processedOnDate).isNotNull()
    }

    @ParameterizedTest
    @EnumSource(value = ExternalEventStatus::class, names = ["PROCESSING"], mode = EnumSource.Mode.EXCLUDE)
    fun `should not mark as processed`(status: ExternalEventStatus) {
        // given
        val externalEvent = externalEvent(status = status)

        // when
        val result = catchThrowable { externalEvent.markAsProcessed() }

        // then
        assertThat(result).isExactlyInstanceOf(IllegalStateException::class.java)
        assertThat(externalEvent.status).isEqualTo(status)
        assertThat(externalEvent.processedOnDate).isNull()
    }

    @Test
    fun `should mark as failed`() {
        // given
        val externalEvent = externalEvent(status = ExternalEventStatus.PROCESSING)

        // when
        externalEvent.markAsFailed("error message")

        // then
        assertThat(externalEvent.status).isEqualTo(ExternalEventStatus.FAILED)
        assertThat(externalEvent.errorMessage).isEqualTo("error message")
        assertThat(externalEvent.processedOnDate).isNull()
    }

    @ParameterizedTest
    @EnumSource(value = ExternalEventStatus::class, names = ["PROCESSING"], mode = EnumSource.Mode.EXCLUDE)
    fun `should not mark as failed`(status: ExternalEventStatus) {
        // given
        val externalEvent = externalEvent(status = status)

        // when
        val result = catchThrowable { externalEvent.markAsFailed("error message") }

        // then
        assertThat(result).isExactlyInstanceOf(IllegalStateException::class.java)
        assertThat(externalEvent.status).isEqualTo(status)
        assertThat(externalEvent.errorMessage).isNull()
        assertThat(externalEvent.processedOnDate).isNull()
    }
}