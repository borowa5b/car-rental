package pl.borowa5b.car.rental.domain.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.domain.DomainException
import pl.borowa5b.car.rental.domain.model.CustomerId

class CustomerNotFoundException(customerId: CustomerId) :
    DomainException(Status.NOT_FOUND, "Customer with id ${customerId.value} not found")