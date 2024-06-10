package pl.borowa5b.car.rental.events.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.externalEvent
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.domain.shared.vo.ExternalEventId

class ExternalEventRepositoryTest {

    private val externalEventRepository: ExternalEventRepository = TestExternalEventRepository()

    @BeforeEach
    fun `before each`() {
        (externalEventRepository as TestExternalEventRepository).deleteAll()
    }

    @Test
    fun `should save external event`() {
        // given
        val externalEvent = externalEvent()

        // when
        val result = externalEventRepository.save(externalEvent)

        // then
        assertThat(result.id).isEqualTo(externalEvent.id)
    }

    @Test
    fun `should find check if external event exists`() {
        // given
        val externalEvent = externalEvent()
        externalEventRepository.save(externalEvent)

        // when
        val result = externalEventRepository.exists(externalEvent.id)

        // then
        assertThat(result).isTrue()
    }

    private class TestExternalEventRepository(private val externalEvents: ArrayList<ExternalEvent> = ArrayList()) :
        ExternalEventRepository {

        override fun save(externalEvent: ExternalEvent): ExternalEvent {
            externalEvents.add(externalEvent)
            return externalEvent
        }

        override fun exists(id: ExternalEventId): Boolean = externalEvents.any { it.id == id }

        fun deleteAll() = externalEvents.clear();
    }
}