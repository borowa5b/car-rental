package pl.borowa5b.car.rental.events.infrastructure

import nl.altindag.log.LogCaptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.applicationEvent
import pl.borowa5b.car.rental.events.domain.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus

@ExtendWith(MockitoExtension::class)
class EventQueuePublisherTest {

    @Mock
    private lateinit var repository: ApplicationEventRepository

    @Mock
    private lateinit var externalQueuePublisher: ExternalQueuePublisher

    @InjectMocks
    private lateinit var publisher: EventQueuePublisher

    @Test
    fun `should publish event`() {
        // given
        val event = applicationEvent(status = ApplicationEventStatus.NEW)

        // when
        publisher.publish(event)

        // then
        assertThat(event.status).isEqualTo(ApplicationEventStatus.PUBLISHED)

        verify(repository, times(2)).save(event)
        verify(externalQueuePublisher).publish(event)

        verifyNoMoreInteractions(repository, externalQueuePublisher)
    }

    @Test
    fun `should not publish event`() {
        // given
        val logCaptor = LogCaptor.forName(EventQueuePublisher::class.simpleName)
        val event = applicationEvent(status = ApplicationEventStatus.NEW)
        whenever(externalQueuePublisher.publish(event)).thenReturn(Result.failure(IllegalStateException()))

        // when
        publisher.publish(event)

        // then
        assertThat(logCaptor.warnLogs.first()).isEqualTo("Failed to publish event with id ${event.id}")
        assertThat(event.status).isEqualTo(ApplicationEventStatus.FAILED)

        verify(repository, times(2)).save(event)
        verify(externalQueuePublisher).publish(event)

        verifyNoMoreInteractions(repository, externalQueuePublisher)
    }
}