package pl.borowa5b.car.rental.cars.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import pl.borowa5b.car.rental.cars.domain.command.CommandObjects.editCarCommand
import pl.borowa5b.car.rental.cars.domain.event.CarEditedEvent
import pl.borowa5b.car.rental.cars.domain.exception.CarHasActiveRentalsException
import pl.borowa5b.car.rental.cars.domain.shared.exception.CarNotFoundException
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.events.domain.shared.ApplicationEventPublisher
import pl.borowa5b.car.rental.rentals.domain.shared.repository.RentalRepository

@ExtendWith(MockitoExtension::class)
class CarEditorTest {

    @Mock
    private lateinit var carRepository: CarRepository

    @Mock
    private lateinit var rentalRepository: RentalRepository

    @Mock
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMocks
    private lateinit var carEditor: CarEditor

    @Test
    fun `should edit car`() {
        // given
        val carId = carId()
        val car = car(id = carId)
        val command = editCarCommand(carId = carId)
        whenever(carRepository.existsBy(carId)).thenReturn(true)
        whenever(rentalRepository.hasActiveRentals(carId)).thenReturn(false)
        whenever(carRepository.findBy(carId)).thenReturn(car)

        // when
        carEditor.edit(command)

        // then
        assertThat(car.brand).isEqualTo(command.brand)
        assertThat(car.model).isEqualTo(command.getModel())
        assertThat(car.generation).isEqualTo(command.getGeneration())
        assertThat(car.productionYear).isEqualTo(command.productionYear)
        assertThat(car.color).isEqualTo(command.getColor())
        assertThat(car.pricePerDay).isEqualTo(command.pricePerDay)
        assertThat(car.quantity).isEqualTo(command.quantity)

        verify(carRepository).existsBy(carId)
        verify(rentalRepository).hasActiveRentals(carId)
        verify(carRepository).findBy(carId)
        verify(carRepository).save(car)
        verify(applicationEventPublisher).publish(any<CarEditedEvent>())
        verifyNoMoreInteractions(carRepository, rentalRepository, applicationEventPublisher)
    }

    @Test
    fun `should throw exception when car does not exist`() {
        // given
        val command = editCarCommand()
        whenever(carRepository.existsBy(command.carId)).thenReturn(false)

        // when
        val result = catchThrowable { carEditor.edit(command) }

        // then
        assertThat(result).isInstanceOf(CarNotFoundException::class.java)
            .hasMessage("Car with id ${command.carId.value} not found")

        verify(carRepository).existsBy(command.carId)
        verifyNoMoreInteractions(carRepository)
        verifyNoInteractions(rentalRepository, applicationEventPublisher)
    }

    @Test
    fun `should throw exception when car has active rentals`() {
        // given
        val command = editCarCommand()
        whenever(carRepository.existsBy(command.carId)).thenReturn(true)
        whenever(rentalRepository.hasActiveRentals(command.carId)).thenReturn(true)

        // when
        val result = catchThrowable { carEditor.edit(command) }

        // then
        assertThat(result).isInstanceOf(CarHasActiveRentalsException::class.java)
            .hasMessage("Car with id ${command.carId.value} has active rentals.")

        verify(carRepository).existsBy(command.carId)
        verify(rentalRepository).hasActiveRentals(command.carId)
        verifyNoMoreInteractions(carRepository, rentalRepository)
        verifyNoInteractions(applicationEventPublisher)
    }
}