package pl.borowa5b.car.rental.infrastructure.scheduler

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pl.borowa5b.car.rental.domain.EventPuller

@ExtendWith(MockitoExtension::class)
class EventPullerSchedulerTest {

    @Mock
    private lateinit var eventPuller: EventPuller

    @InjectMocks
    private lateinit var eventPullerScheduler: EventPullerScheduler

    @Test
    fun `should pull events`() {
        // given

        // when
        eventPullerScheduler.pullEvents()

        // then
        verify(eventPuller).pull()
        verifyNoMoreInteractions(eventPuller)
    }
}