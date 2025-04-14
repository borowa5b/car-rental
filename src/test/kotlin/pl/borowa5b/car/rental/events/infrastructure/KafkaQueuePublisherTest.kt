package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.applicationEvent
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import java.util.concurrent.CompletableFuture

@ExtendWith(MockitoExtension::class)
class KafkaQueuePublisherTest {

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Test
    fun `should publish to kafka queue`() {
        // given
        val topicId = "car-rental-topic"
        val publisher = KafkaQueuePublisher(topicId, objectMapper, kafkaTemplate)
        val event = applicationEvent(status = ApplicationEventStatus.NEW)
        val eventPayload = "{}"
        val sendResult = mock<SendResult<String, String>>()
        whenever(objectMapper.writeValueAsString(event)).thenReturn(eventPayload)
        whenever(kafkaTemplate.send(topicId, eventPayload)).thenReturn(CompletableFuture.completedFuture(sendResult))

        // when
        val result = publisher.publish(event)

        // then
        assertThat(result.isSuccess).isTrue()

        verify(kafkaTemplate).send(topicId, eventPayload)
        verify(objectMapper).writeValueAsString(event)

        verifyNoMoreInteractions(kafkaTemplate, objectMapper)
    }

    @Test
    fun `should not publish to kafka queue`() {
        // given
        val topicId = "car-rental-topic"
        val publisher = KafkaQueuePublisher(topicId, objectMapper, kafkaTemplate)
        val event = applicationEvent(status = ApplicationEventStatus.NEW)
        val eventPayload = "{}"
        whenever(objectMapper.writeValueAsString(event)).thenReturn(eventPayload)
        whenever(kafkaTemplate.send(topicId, eventPayload)).thenReturn(
            CompletableFuture.failedFuture(
                IllegalStateException()
            )
        )

        // when
        val result = publisher.publish(event)

        // then
        assertThat(result.isFailure).isTrue()

        verify(kafkaTemplate).send(topicId, eventPayload)
        verify(objectMapper).writeValueAsString(event)

        verifyNoMoreInteractions(kafkaTemplate, objectMapper)
    }
}