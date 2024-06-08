package pl.borowa5b.car.rental.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.DomainObjects.externalEvent
import pl.borowa5b.car.rental.domain.model.ExternalEvent
import pl.borowa5b.car.rental.domain.model.vo.ExternalEventId
import pl.borowa5b.car.rental.infrastructure.entity.ExternalEventEntity

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
        assertThat(result.id).isEqualTo(externalEvent.id.value)
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

    private class TestExternalEventRepository(private val externalEvents: ArrayList<ExternalEventEntity> = ArrayList()) :
        ExternalEventRepository {

        override fun save(externalEvent: ExternalEvent): ExternalEventEntity {
            val entity = ExternalEventEntity.fromDomain(externalEvent)
            externalEvents.add(entity)
            return entity
        }

        override fun exists(id: ExternalEventId): Boolean = externalEvents.any { it.id == id.value }

        fun deleteAll() = externalEvents.clear();
    }
}