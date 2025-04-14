package pl.borowa5b.car.rental.events.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.test.RabbitListenerTest
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.IntegrationTestApplicationEventMessagePreparer.prepareRentalMadeEventMessage
import pl.borowa5b.car.rental.shared.helper.IntegrationTestAssertions.assertCarQuantity
import pl.borowa5b.car.rental.shared.helper.IntegrationTestEntityCreator.createCarEntity
import java.util.concurrent.TimeUnit


@IntegrationTest
@RabbitListenerTest(capture = true, spy = false)
class RabbitMqEventListenerIT {

    companion object {

        @JvmStatic
        @BeforeAll
        fun `before all`() {
            System.setProperty("car-rental.events-queue-provider", "rabbitmq")
        }

        @JvmStatic
        @AfterAll
        fun `after all`() {
            System.setProperty("car-rental.events-queue-provider", "none")
        }
    }

    @Value("\${spring.rabbitmq.exchange}")
    private lateinit var exchange: String

    @Value("\${spring.rabbitmq.routing-key}")
    private lateinit var routingKey: String

    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate

    @Autowired
    private lateinit var harness: RabbitListenerTestHarness

    @Test
    fun `should handle external event message`() {
        // given
        val carQuantityBefore = 10
        val carId = carId()
        createCarEntity(id = carId.value, quantity = carQuantityBefore)
        val eventMessage = prepareRentalMadeEventMessage(carId = carId)

        // when
        rabbitTemplate.convertAndSend(exchange, routingKey, eventMessage)

        // then
        val invocationData = harness.getNextInvocationDataFor("event-listener", 1, TimeUnit.SECONDS)
        assertThat(invocationData).isNotNull()
        assertThat(invocationData.arguments[0]).isEqualTo(eventMessage)

        assertCarQuantity(carId.value, carQuantityBefore - 1)
    }
}