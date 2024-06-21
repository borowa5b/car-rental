package pl.borowa5b.car.rental.rentals.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever
import pl.borowa5b.car.rental.cars.domain.shared.exception.CarNotFoundException
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.customers.domain.shared.exception.CustomerNotFoundException
import pl.borowa5b.car.rental.customers.domain.shared.repository.CustomerRepository
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.events.domain.shared.ApplicationEventPublisher
import pl.borowa5b.car.rental.rentals.domain.command.CommandObjects.calculateRentalCommand
import pl.borowa5b.car.rental.rentals.domain.command.CommandObjects.makeRentalCommand
import pl.borowa5b.car.rental.rentals.domain.event.RentalMadeEvent
import pl.borowa5b.car.rental.rentals.domain.exception.CustomerHasActiveRentalsException
import pl.borowa5b.car.rental.rentals.domain.generator.RentalIdGenerator
import pl.borowa5b.car.rental.rentals.domain.model.DomainObjects.rental
import pl.borowa5b.car.rental.rentals.domain.model.Rental
import pl.borowa5b.car.rental.rentals.domain.repository.RentalRepository
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import pl.borowa5b.car.rental.rentals.domain.vo.ValueObjects.rentalId
import java.math.BigDecimal
import java.time.OffsetDateTime

@ExtendWith(MockitoExtension::class)
class RentalMakerTest {

    @Mock
    private lateinit var rentalIdGenerator: RentalIdGenerator

    @Mock
    private lateinit var rentalPriceCalculator: RentalPriceCalculator

    @Mock
    private lateinit var rentalRepository: RentalRepository

    @Mock
    private lateinit var carRepository: CarRepository

    @Mock
    private lateinit var customerRepository: CustomerRepository

    @Mock
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMocks
    private lateinit var rentalMaker: RentalMaker

    private val rentalId = rentalId()
    private val carId = carId()
    private val customerId = customerId()
    private val startDate = OffsetDateTime.parse("2022-01-01T12:00:00Z")
    private val endDate = OffsetDateTime.parse("2022-01-02T12:00:00Z")

    @Test
    fun `should make new rental`() {
        // given
        val price = BigDecimal.TEN
        val command = makeRentalCommand(
            carId = carId, customerId = customerId, startDate = startDate, endDate = endDate
        )
        val calculateRentalCommand = calculateRentalCommand(startDate = startDate, endDate = endDate)

        whenever(carRepository.existsBy(carId)).thenReturn(true)
        whenever(customerRepository.exists(customerId)).thenReturn(true)
        whenever(rentalRepository.hasActiveRentals(customerId)).thenReturn(false)
        whenever(rentalPriceCalculator.calculate(calculateRentalCommand)).thenReturn(price)
        whenever(rentalIdGenerator.generate()).thenReturn(rentalId)
        whenever(rentalRepository.save(any<Rental>())).thenReturn(
            rental(
                id = rentalId,
                carId = carId,
                customerId = customerId,
                status = RentalStatus.NEW,
                price = price,
                startDate = startDate,
                endDate = endDate
            )
        )

        // when
        val result = rentalMaker.make(command)

        // then
        assertThat(result).isEqualTo(rentalId)

        verify(carRepository).existsBy(carId)
        verify(customerRepository).exists(customerId)
        verify(rentalRepository).hasActiveRentals(customerId)
        verify(rentalPriceCalculator).calculate(calculateRentalCommand)
        verify(rentalIdGenerator).generate()
        verify(rentalRepository).save(any<Rental>())
        verify(applicationEventPublisher).publish(any<RentalMadeEvent>())
        verifyNoMoreInteractions(
            carRepository,
            customerRepository,
            rentalPriceCalculator,
            rentalIdGenerator,
            rentalRepository,
            applicationEventPublisher
        )
    }

    @Test
    fun `should throw exception when car does not exist`() {
        // given
        val command =
            makeRentalCommand(carId = carId, customerId = customerId, startDate = startDate, endDate = endDate)

        whenever(carRepository.existsBy(carId)).thenReturn(false)

        // when
        val result = catchThrowable { rentalMaker.make(command) }

        // then
        assertThat(result).isExactlyInstanceOf(CarNotFoundException::class.java)

        verify(carRepository).existsBy(carId)
        verifyNoMoreInteractions(carRepository)
        verifyNoInteractions(
            customerRepository,
            rentalPriceCalculator,
            rentalIdGenerator,
            rentalRepository,
            applicationEventPublisher
        )
    }

    @Test
    fun `should throw exception when customer does not exist`() {
        // given
        val command =
            makeRentalCommand(carId = carId, customerId = customerId, startDate = startDate, endDate = endDate)

        whenever(carRepository.existsBy(carId)).thenReturn(true)
        whenever(customerRepository.exists(customerId)).thenReturn(false)

        // when
        val result = catchThrowable { rentalMaker.make(command) }

        // then
        assertThat(result).isExactlyInstanceOf(CustomerNotFoundException::class.java)

        verify(carRepository).existsBy(carId)
        verify(customerRepository).exists(customerId)
        verifyNoMoreInteractions(carRepository, customerRepository)
        verifyNoInteractions(rentalPriceCalculator, rentalIdGenerator, rentalRepository, applicationEventPublisher)
    }

    @Test
    fun `should throw exception when customer has active rentals`() {
        // given
        val command =
            makeRentalCommand(carId = carId, customerId = customerId, startDate = startDate, endDate = endDate)

        whenever(carRepository.existsBy(carId)).thenReturn(true)
        whenever(customerRepository.exists(customerId)).thenReturn(true)
        whenever(rentalRepository.hasActiveRentals(customerId)).thenReturn(true)

        // when
        val result = catchThrowable { rentalMaker.make(command) }

        // then
        assertThat(result).isExactlyInstanceOf(CustomerHasActiveRentalsException::class.java)

        verify(carRepository).existsBy(carId)
        verify(customerRepository).exists(customerId)
        verify(rentalRepository).hasActiveRentals(customerId)
        verifyNoMoreInteractions(carRepository, customerRepository, rentalRepository)
        verifyNoInteractions(rentalPriceCalculator, rentalIdGenerator, applicationEventPublisher)
    }
}