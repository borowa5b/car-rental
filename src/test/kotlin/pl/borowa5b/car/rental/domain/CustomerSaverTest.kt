package pl.borowa5b.car.rental.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import pl.borowa5b.car.rental.domain.command.CommandObjects.saveCustomerCommand
import pl.borowa5b.car.rental.domain.model.Customer
import pl.borowa5b.car.rental.domain.repository.CustomerRepository

@ExtendWith(MockitoExtension::class)
class CustomerSaverTest {

    @Mock
    private lateinit var customerRepository: CustomerRepository

    @InjectMocks
    private lateinit var customerSaver: CustomerSaver

    @Test
    fun `should save customer`() {
        // given
        val command = saveCustomerCommand()

        // when
        customerSaver.save(command)

        // then
        verify(customerRepository).save(any<Customer>())
        verifyNoMoreInteractions(customerRepository)
    }
}