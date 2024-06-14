package pl.borowa5b.car.rental.rentals.infrastructure.scheduler

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pl.borowa5b.car.rental.rentals.domain.RentalStarter

@ExtendWith(MockitoExtension::class)
class RentalStarterSchedulerTest {

    @Mock
    private lateinit var rentalStarter: RentalStarter

    @InjectMocks
    private lateinit var rentalStarterScheduler: RentalStarterScheduler

    @Test
    fun `should start rentals`() {
        // given

        // when
        rentalStarterScheduler.startRentals()

        // then
        verify(rentalStarter).start()
        verifyNoMoreInteractions(rentalStarter)
    }
}