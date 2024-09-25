package pl.borowa5b.car.rental.events.infrastructure

import nl.altindag.log.LogCaptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.externalEvent
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus
import pl.borowa5b.car.rental.events.infrastructure.entity.ExternalEventEntity
import pl.borowa5b.car.rental.shared.helper.Database
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.TestSpringExternalEventRepository

@IntegrationTest
class ExternalEventProcessorIT {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var processor: ExternalEventProcessor

    @Autowired
    private lateinit var externalEventRepository: TestSpringExternalEventRepository

    @BeforeEach
    fun `before each`() {
        database.prepare()
    }

    @Test
    fun `should process new event`() {
        // given
        val event = externalEvent(status = ExternalEventStatus.NEW)

        // when
        processor.process(event)

        // then
        val processedEvent = externalEventRepository.findById(event.id.value).get()
        assertThat(processedEvent.status).isEqualTo(ExternalEventStatus.PROCESSED)
        assertThat(processedEvent.processedOnDate).isNotNull()
    }

    @Test
    fun `should not save unsupported event`() {
        // given
        val event = externalEvent(type = "UnsupportedEvent", version = "1.0")

        // when
        processor.process(event)

        // then
        val processedEvent = externalEventRepository.findById(event.id.value).orElse(null)
        assertThat(processedEvent).isNull()
    }

    @Test
    fun `should skip already processed event`() {
        // given
        val logCaptor = LogCaptor.forName(ExternalEventProcessor::class.simpleName)
        val event = externalEvent(status = ExternalEventStatus.PROCESSED)
        externalEventRepository.save(ExternalEventEntity.fromDomain(event))

        // when
        processor.process(event)

        // then
        val processedEvent = externalEventRepository.findById(event.id.value).orElse(null)
        assertThat(processedEvent.status).isEqualTo(ExternalEventStatus.PROCESSED)

        val warnLogs = logCaptor.warnLogs
        assertThat(warnLogs).hasSize(1)

        val warnLog = warnLogs[0]
        assertThat(warnLog).isEqualTo("Skipping event with id ${processedEvent.id} because it is already processed")
    }

    @Test
    fun `should mark event as failed if processing fails`() {
        // given
        val event = externalEvent(payload = "invalid json")

        // when
        processor.process(event)

        // then
        val processedEvent = externalEventRepository.findById(event.id.value).get()
        assertThat(processedEvent.status).isEqualTo(ExternalEventStatus.FAILED)
        assertThat(processedEvent.errorMessage).isEqualTo(
            """
                Unrecognized token 'invalid': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')
                 at [Source: (String)"invalid json"; line: 1, column: 8]
            """.trimIndent()
        )
    }
}