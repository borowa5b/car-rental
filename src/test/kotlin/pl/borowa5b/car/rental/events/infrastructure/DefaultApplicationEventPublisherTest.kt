package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import pl.borowa5b.car.rental.events.domain.DomainEvent
import pl.borowa5b.car.rental.events.domain.generator.EventIdGenerator
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.events.domain.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.events.domain.vo.ValueObjects.eventId

@ExtendWith(MockitoExtension::class)
class DefaultApplicationEventPublisherTest {

    @Mock
    private lateinit var applicationEventRepository: ApplicationEventRepository

    @Mock
    private lateinit var eventIdGenerator: EventIdGenerator

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var eventQueuePublisher: EventQueuePublisher

    @InjectMocks
    private lateinit var defaultApplicationEventPublisher: DefaultApplicationEventPublisher

    @Test
    fun `should publish application event`() {
        // given
        val event = object : DomainEvent {
            override fun getType(): String = "ApplicationEvent"
            override fun getVersion(): String = "1.0"
        }
        whenever(objectMapper.writeValueAsString(event)).thenReturn("")
        whenever(eventIdGenerator.generate()).thenReturn(eventId())

        // when
        defaultApplicationEventPublisher.publish(event)

        // then
        verify(objectMapper).writeValueAsString(event)
        verify(eventIdGenerator).generate()
        verify(applicationEventRepository).save(any<ApplicationEvent>())
        verify(eventQueuePublisher).publish(any<ApplicationEvent>())
        verifyNoMoreInteractions(objectMapper, eventIdGenerator, applicationEventRepository)
    }
}