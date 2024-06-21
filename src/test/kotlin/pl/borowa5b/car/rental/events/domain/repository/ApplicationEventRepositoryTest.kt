package pl.borowa5b.car.rental.events.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.applicationEvent

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
        assertThat(result.id).isEqualTo(applicationEvent.id)
    }

    private class TestApplicationEventRepository(private val applicationEvents: ArrayList<ApplicationEvent> = ArrayList()) :
        ApplicationEventRepository {

        override fun save(applicationEvent: ApplicationEvent): ApplicationEvent {
            applicationEvents.add(applicationEvent)
            return applicationEvent
        }

        override fun findAll(): List<ApplicationEvent> = applicationEvents

        fun deleteAll() = applicationEvents.clear();
    }
}