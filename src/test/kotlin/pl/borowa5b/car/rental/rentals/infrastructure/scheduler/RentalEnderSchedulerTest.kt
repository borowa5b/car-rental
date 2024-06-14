package pl.borowa5b.car.rental.rentals.infrastructure.scheduler

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pl.borowa5b.car.rental.rentals.domain.RentalEnder

@ExtendWith(MockitoExtension::class)
class RentalEnderSchedulerTest {

    @Mock
    private lateinit var rentalEnder: RentalEnder

    @InjectMocks
    private lateinit var rentalEnderScheduler: RentalEnderScheduler

    @Test
    fun `should end rentals`() {
        // given

        // when
        rentalEnderScheduler.endRentals()

        // then
        verify(rentalEnder).end()
        verifyNoMoreInteractions(rentalEnder)
    }
}