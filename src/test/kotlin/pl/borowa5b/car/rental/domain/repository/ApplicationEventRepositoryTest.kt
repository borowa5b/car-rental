package pl.borowa5b.car.rental.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.domain.model.DomainObjects.applicationEvent
import pl.borowa5b.car.rental.infrastructure.entity.ApplicationEventEntity

class ApplicationEventRepositoryTest {

    private val applicationEventRepository: ApplicationEventRepository = TestApplicationEventRepository()

    @BeforeEach
    fun `before each`() {
        (applicationEventRepository as TestApplicationEventRepository).deleteAll()
    }

    @Test
    fun `should save application event`() {
        // given
        val applicationEvent = applicationEvent()

        // when
        val result = applicationEventRepository.save(applicationEvent)

        // then
        assertThat(result.id).isEqualTo(applicationEvent.id.value)
    }

    private class TestApplicationEventRepository(private val applicationEvents: ArrayList<ApplicationEventEntity> = ArrayList()) :
        ApplicationEventRepository {

        override fun save(applicationEvent: ApplicationEvent): ApplicationEventEntity {
            val entity = ApplicationEventEntity.fromDomain(applicationEvent)
            applicationEvents.add(entity)
            return entity
        }

        fun deleteAll() = applicationEvents.clear();
    }
}