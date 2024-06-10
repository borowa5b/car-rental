package pl.borowa5b.car.rental.customers.domain.shared.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.shared.domain.DomainException

class CustomerNotFoundException(customerId: CustomerId) :
    DomainException(Status.NOT_FOUND, "Customer with id ${customerId.value} not found")