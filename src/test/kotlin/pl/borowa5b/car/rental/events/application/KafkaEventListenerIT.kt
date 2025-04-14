package pl.borowa5b.car.rental.events.application

import org.awaitility.Awaitility.await
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.IntegrationTestApplicationEventMessagePreparer.prepareRentalMadeEventMessage
import pl.borowa5b.car.rental.shared.helper.IntegrationTestAssertions.assertCarQuantity
import pl.borowa5b.car.rental.shared.helper.IntegrationTestEntityCreator.createCarEntity
import java.util.concurrent.TimeUnit

@IntegrationTest
@EmbeddedKafka(partitions = 1, topics = ["\${spring.kafka.topic-id}"])
class KafkaEventListenerIT {

    @Value("\${spring.kafka.topic-id}")
    private lateinit var topicId: String

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    companion object {

        @JvmStatic
        @BeforeAll
        fun `before all`() {
            System.setProperty("car-rental.events-queue-provider", "kafka")
        }

        @JvmStatic
        @AfterAll
        fun `after all`() {
            System.setProperty("car-rental.events-queue-provider", "none")
        }
    }

    @Test
    fun `should handle external event message`() {
        // given
        val carQuantityBefore = 10
        val carId = carId()
        createCarEntity(id = carId.value, quantity = carQuantityBefore)
        val eventMessage = prepareRentalMadeEventMessage(carId = carId)

        // when
        kafkaTemplate.send(topicId, eventMessage)

        // then
        await().atMost(1, TimeUnit.SECONDS).untilAsserted {
            assertCarQuantity(carId.value, carQuantityBefore - 1)
        }
    }
}