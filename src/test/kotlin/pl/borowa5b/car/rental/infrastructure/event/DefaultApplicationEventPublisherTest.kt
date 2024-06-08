package pl.borowa5b.car.rental.infrastructure.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pl.borowa5b.car.rental.domain.event.DomainEventObjects.rentalMadeEvent
import pl.borowa5b.car.rental.domain.generator.ApplicationEventIdGenerator
import pl.borowa5b.car.rental.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.domain.model.DomainObjects.applicationEventId
import pl.borowa5b.car.rental.domain.repository.ApplicationEventRepository

@ExtendWith(MockitoExtension::class)
class DefaultApplicationEventPublisherTest {

    @Mock
    private lateinit var applicationEventRepository: ApplicationEventRepository

    @Mock
    private lateinit var applicationEventIdGenerator: ApplicationEventIdGenerator

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @InjectMocks
    private lateinit var defaultApplicationEventPublisher: DefaultApplicationEventPublisher

    @Test
    fun `should publish application event`() {
        // given
        val event = rentalMadeEvent()
        `when`(objectMapper.writeValueAsString(event)).thenReturn("")
        `when`(applicationEventIdGenerator.generate()).thenReturn(applicationEventId())

        // when
        defaultApplicationEventPublisher.publish(event)

        // then
        verify(objectMapper).writeValueAsString(event)
        verify(applicationEventIdGenerator).generate()
        verify(applicationEventRepository).save(any<ApplicationEvent>())
        verifyNoMoreInteractions(objectMapper, applicationEventIdGenerator, applicationEventRepository)
    }
}