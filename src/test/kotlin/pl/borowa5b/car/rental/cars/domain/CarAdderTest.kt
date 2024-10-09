package pl.borowa5b.car.rental.cars.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import pl.borowa5b.car.rental.cars.domain.command.CommandObjects.addCarCommand
import pl.borowa5b.car.rental.cars.domain.event.CarAddedEvent
import pl.borowa5b.car.rental.cars.domain.exception.CarAlreadyExistsException
import pl.borowa5b.car.rental.cars.domain.generator.CarIdGenerator
import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.events.domain.shared.ApplicationEventPublisher

@ExtendWith(MockitoExtension::class)
class CarAdderTest {

    @Mock
    private lateinit var carIdGenerator: CarIdGenerator

    @Mock
    private lateinit var carRepository: CarRepository

    @Mock
    private lateinit var applicationEventPublished: ApplicationEventPublisher

    @InjectMocks
    private lateinit var carAdder: CarAdder

    @Test
    fun `should add car`() {
        // given
        val command = addCarCommand()
        val carId = carId()
        whenever(
            carRepository.existsBy(
                command.brand,
                command.getModel(),
                command.getGeneration(),
                command.productionYear,
                command.getColor()
            )
        ).thenReturn(false)
        whenever(carIdGenerator.generate()).thenReturn(carId)
        whenever(carRepository.save(any<Car>())).thenReturn(car(id = carId))

        // when
        val result = carAdder.add(command)

        // then
        assertThat(result).isEqualTo(carId)

        verify(carRepository).existsBy(
            command.brand,
            command.getModel(),
            command.getGeneration(),
            command.productionYear,
            command.getColor()
        )
        verify(carIdGenerator).generate()
        verify(carRepository).save(any<Car>())
        verify(applicationEventPublished).publish(any<CarAddedEvent>())
        verifyNoMoreInteractions(carRepository, applicationEventPublished)
    }

    @Test
    fun `should throw exception when car already exists`() {
        // given
        val command = addCarCommand()
        whenever(
            carRepository.existsBy(
                command.brand,
                command.getModel(),
                command.getGeneration(),
                command.productionYear,
                command.getColor()
            )
        ).thenReturn(true)

        // when
        val result = catchThrowable { carAdder.add(command) }

        // then
        assertThat(result).isExactlyInstanceOf(CarAlreadyExistsException::class.java)
            .hasMessage("Car ${command.brand} ${command.getModel()} ${command.getColor()} from ${command.productionYear} already exists.")

        verify(carRepository).existsBy(
            command.brand,
            command.getModel(),
            command.getGeneration(),
            command.productionYear,
            command.getColor()
        )
        verifyNoMoreInteractions(carRepository)
        verifyNoInteractions(carIdGenerator, applicationEventPublished)
    }
}