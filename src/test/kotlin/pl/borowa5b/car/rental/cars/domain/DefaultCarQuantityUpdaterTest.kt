package pl.borowa5b.car.rental.cars.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import pl.borowa5b.car.rental.cars.domain.shared.exception.CarNotFoundException
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId

@ExtendWith(MockitoExtension::class)
class DefaultCarQuantityUpdaterTest {

    @Mock
    private lateinit var carRepository: CarRepository

    @InjectMocks
    private lateinit var carQuantityUpdater: DefaultCarQuantityUpdater

    @Test
    fun `should increase quantity`() {
        // given
        val carId = carId()
        val car = car(id = carId, quantity = 10)
        whenever(carRepository.findBy(carId)).thenReturn(car)

        // when
        carQuantityUpdater.increase(carId)

        // then
        assertThat(car.quantity).isEqualTo(11)

        verify(carRepository).findBy(carId)
        verify(carRepository).save(car)
        verifyNoMoreInteractions(carRepository)
    }

    @Test
    fun `should not increase quantity when car is not found`() {
        // given
        val carId = carId()
        whenever(carRepository.findBy(carId)).thenReturn(null)

        // when
        val result = catchThrowable { carQuantityUpdater.increase(carId) }

        // then
        assertThat(result).isExactlyInstanceOf(CarNotFoundException::class.java)
            .hasMessage("Car with id ${carId.value} not found.")

        verify(carRepository).findBy(carId)
        verifyNoMoreInteractions(carRepository)
    }

    @Test
    fun `should decrease quantity`() {
        // given
        val carId = carId()
        val car = car(id = carId, quantity = 10)
        whenever(carRepository.findBy(carId)).thenReturn(car)

        // when
        carQuantityUpdater.decrease(carId)

        // then
        assertThat(car.quantity).isEqualTo(9)

        verify(carRepository).findBy(carId)
        verify(carRepository).save(car)
        verifyNoMoreInteractions(carRepository)
    }

    @Test
    fun `should not decrease quantity when car is not found`() {
        // given
        val carId = carId()
        whenever(carRepository.findBy(carId)).thenReturn(null)

        // when
        val result = catchThrowable { carQuantityUpdater.decrease(carId) }

        // then
        assertThat(result).isExactlyInstanceOf(CarNotFoundException::class.java)
            .hasMessage("Car with id ${carId.value} not found.")

        verify(carRepository).findBy(carId)
        verifyNoMoreInteractions(carRepository)
    }
}