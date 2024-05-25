package pl.borowa5b.car.rental.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import pl.borowa5b.car.rental.domain.command.CommandObjects.makeRentalCommand
import pl.borowa5b.car.rental.domain.generator.RentalIdGenerator
import pl.borowa5b.car.rental.domain.model.DomainObjects.carId
import pl.borowa5b.car.rental.domain.model.DomainObjects.customerId
import pl.borowa5b.car.rental.domain.model.DomainObjects.rentalId
import pl.borowa5b.car.rental.domain.model.RentalStatus
import pl.borowa5b.car.rental.domain.repository.CarRepository
import pl.borowa5b.car.rental.domain.repository.CustomerRepository
import pl.borowa5b.car.rental.domain.repository.RentalRepository
import pl.borowa5b.car.rental.infrastructure.entity.EntityObjects.rentalEntity
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
        val command =
            makeRentalCommand(carId = carId, customerId = customerId, startDate = startDate, endDate = endDate)

        `when`(carRepository.exists(carId)).thenReturn(true)
        `when`(customerRepository.exists(customerId)).thenReturn(true)
        `when`(rentalRepository.hasActiveRentals(customerId)).thenReturn(false)
        `when`(rentalPriceCalculator.calculate(startDate, endDate)).thenReturn(price)
        `when`(rentalIdGenerator.generate()).thenReturn(rentalId)
        `when`(rentalRepository.save(any())).thenReturn(
            rentalEntity(
                id = rentalId.value,
                carId = carId.value,
                customerId = customerId.value,
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

        verify(carRepository).exists(carId)
        verify(customerRepository).exists(customerId)
        verify(rentalRepository).hasActiveRentals(customerId)
        verify(rentalPriceCalculator).calculate(startDate, endDate)
        verify(rentalIdGenerator).generate()
        verify(rentalRepository).save(any())
        verifyNoMoreInteractions(
            carRepository,
            customerRepository,
            rentalPriceCalculator,
            rentalIdGenerator,
            rentalRepository
        )
    }

    @Test
    fun `should throw exception when car does not exist`() {
        // given
        val command =
            makeRentalCommand(carId = carId, customerId = customerId, startDate = startDate, endDate = endDate)

        `when`(carRepository.exists(carId)).thenReturn(false)

        // when
        val result = catchThrowable { rentalMaker.make(command) }

        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException::class.java)

        verify(carRepository).exists(carId)
        verifyNoMoreInteractions(carRepository)
    }

    @Test
    fun `should throw exception when customer does not exist`() {
        // given
        val command =
            makeRentalCommand(carId = carId, customerId = customerId, startDate = startDate, endDate = endDate)

        `when`(carRepository.exists(carId)).thenReturn(true)
        `when`(customerRepository.exists(customerId)).thenReturn(false)

        // when
        val result = catchThrowable { rentalMaker.make(command) }

        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException::class.java)

        verify(carRepository).exists(carId)
        verify(customerRepository).exists(customerId)
        verifyNoMoreInteractions(carRepository, customerRepository)
    }

    @Test
    fun `should throw exception when customer has active rentals`() {
        // given
        val command =
            makeRentalCommand(carId = carId, customerId = customerId, startDate = startDate, endDate = endDate)

        `when`(carRepository.exists(carId)).thenReturn(true)
        `when`(customerRepository.exists(customerId)).thenReturn(true)
        `when`(rentalRepository.hasActiveRentals(customerId)).thenReturn(true)

        // when
        val result = catchThrowable { rentalMaker.make(command) }

        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException::class.java)

        verify(carRepository).exists(carId)
        verify(customerRepository).exists(customerId)
        verify(rentalRepository).hasActiveRentals(customerId)
        verifyNoMoreInteractions(carRepository, customerRepository, rentalRepository)
    }
}