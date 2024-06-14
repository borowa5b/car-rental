package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.externalEvent
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent

@ExtendWith(MockitoExtension::class)
class FileEventPullerTest {

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @Mock
    private lateinit var externalEventProcessor: ExternalEventProcessor

    @InjectMocks
    private lateinit var fileEventPuller: FileEventPuller

    @Test
    fun `should pull events from file`() {
        // given
        whenever(objectMapper.readValue(any<String>(), any<TypeReference<List<ExternalEvent>>>())).thenReturn(
            listOf(
                externalEvent()
            )
        )

        // when
        fileEventPuller.pull()

        // then
        verify(objectMapper).readValue(any<String>(), any<TypeReference<List<ExternalEvent>>>())
        verify(externalEventProcessor).process(any<ExternalEvent>())
    }
}