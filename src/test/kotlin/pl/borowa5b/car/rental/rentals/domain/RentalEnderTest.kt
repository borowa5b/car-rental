package pl.borowa5b.car.rental.rentals.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import pl.borowa5b.car.rental.cars.domain.shared.CarQuantityUpdater
import pl.borowa5b.car.rental.rentals.domain.model.DomainObjects.rental
import pl.borowa5b.car.rental.rentals.domain.shared.repository.RentalRepository
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.time.OffsetDateTime

@ExtendWith(MockitoExtension::class)
class RentalEnderTest {

    @Mock
    private lateinit var rentalRepository: RentalRepository

    @Mock
    private lateinit var carQuantityUpdater: CarQuantityUpdater

    @InjectMocks
    private lateinit var rentalEnder: RentalEnder

    @Test
    fun `should end rentals`() {
        // given
        val rental = rental(status = RentalStatus.STARTED)
        whenever(rentalRepository.findToEnd(any<OffsetDateTime>())).thenReturn(rental.id).thenReturn(null)
        whenever(rentalRepository.findById(rental.id)).thenReturn(rental)

        // when
        rentalEnder.end()

        // then
        assertThat(rental.status).isEqualTo(RentalStatus.ENDED)

        verify(rentalRepository, times(2)).findToEnd(any<OffsetDateTime>())
        verify(rentalRepository).findById(rental.id)
        verify(carQuantityUpdater).increase(rental.carId)
        verify(rentalRepository).save(rental)
        verifyNoMoreInteractions(rentalRepository, carQuantityUpdater)
    }
}