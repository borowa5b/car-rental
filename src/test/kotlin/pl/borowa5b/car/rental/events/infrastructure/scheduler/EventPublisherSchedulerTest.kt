package pl.borowa5b.car.rental.events.infrastructure.scheduler

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.applicationEvent
import pl.borowa5b.car.rental.events.domain.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.events.infrastructure.EventQueuePublisher

@ExtendWith(MockitoExtension::class)
class EventPublisherSchedulerTest {

    @Mock
    private lateinit var applicationEventRepository: ApplicationEventRepository

    @Mock
    private lateinit var eventPublisher: EventQueuePublisher

    @InjectMocks
    private lateinit var eventPublisherScheduler: EventPublisherScheduler

    @Test
    fun `should publish events`() {
        // given
        val applicationEvent = applicationEvent()
        whenever(applicationEventRepository.findToPublish()).thenReturn(listOf(applicationEvent))

        // when
        eventPublisherScheduler.publishEvents()

        // then
        verify(applicationEventRepository).findToPublish()
        verify(eventPublisher).publish(applicationEvent)
    }
}