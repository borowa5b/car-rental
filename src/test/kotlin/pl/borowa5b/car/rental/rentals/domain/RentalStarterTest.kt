package pl.borowa5b.car.rental.rentals.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import pl.borowa5b.car.rental.rentals.domain.model.DomainObjects.rental
import pl.borowa5b.car.rental.rentals.domain.shared.repository.RentalRepository
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.time.OffsetDateTime

@ExtendWith(MockitoExtension::class)
class RentalStarterTest {

    @Mock
    private lateinit var rentalRepository: RentalRepository

    @InjectMocks
    private lateinit var rentalStarter: RentalStarter

    @Test
    fun `should start rentals`() {
        // given
        val rental = rental(status = RentalStatus.NEW)
        whenever(rentalRepository.findToStart(any<OffsetDateTime>())).thenReturn(rental.id).thenReturn(null)
        whenever(rentalRepository.findById(rental.id)).thenReturn(rental)

        // when
        rentalStarter.start()

        // then
        assertThat(rental.status).isEqualTo(RentalStatus.STARTED)

        verify(rentalRepository, times(2)).findToStart(any<OffsetDateTime>())
        verify(rentalRepository).findById(rental.id)
        verify(rentalRepository).save(rental)
        verifyNoMoreInteractions(rentalRepository)
    }
}