package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.springframework.amqp.rabbit.core.RabbitTemplate
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.applicationEvent
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus

@ExtendWith(MockitoExtension::class)
class RabbitMqQueuePublisherTest {

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var rabbitTemplate: RabbitTemplate

    @Test
    fun `should publish to rabbit mq queue`() {
        // given
        val exchange = "car-rental.rentals-exchange"
        val routingKey = "car-rental.rentals-routing-key"
        val publisher = RabbitMqQueuePublisher(
            exchange = exchange,
            routingKey = routingKey,
            objectMapper = objectMapper,
            rabbitTemplate = rabbitTemplate
        )
        val event = applicationEvent(status = ApplicationEventStatus.NEW)
        val eventPayload = "{}"
        whenever(objectMapper.writeValueAsString(event)).thenReturn(eventPayload)

        // when
        val result = publisher.publish(event)

        // then
        assertThat(result.isSuccess).isTrue()

        verify(rabbitTemplate).convertAndSend(exchange, routingKey, eventPayload)
        verify(objectMapper).writeValueAsString(event)

        verifyNoMoreInteractions(rabbitTemplate, objectMapper)
    }

    @Test
    fun `should not publish to rabbit mq queue`() {
        // given
        val exchange = "car-rental.rentals-exchange"
        val routingKey = "car-rental.rentals-routing-key"
        val publisher = RabbitMqQueuePublisher(
            exchange = exchange,
            routingKey = routingKey,
            objectMapper = objectMapper,
            rabbitTemplate = rabbitTemplate
        )
        val event = applicationEvent(status = ApplicationEventStatus.NEW)
        val eventPayload = "{}"
        whenever(objectMapper.writeValueAsString(event)).thenReturn(eventPayload)
        whenever(rabbitTemplate.convertAndSend(exchange, routingKey, eventPayload)).thenThrow(IllegalStateException())

        // when
        val result = publisher.publish(event)

        // then
        assertThat(result.isFailure).isTrue()

        verify(rabbitTemplate).convertAndSend(exchange, routingKey, eventPayload)
        verify(objectMapper).writeValueAsString(event)

        verifyNoMoreInteractions(rabbitTemplate, objectMapper)
    }
}