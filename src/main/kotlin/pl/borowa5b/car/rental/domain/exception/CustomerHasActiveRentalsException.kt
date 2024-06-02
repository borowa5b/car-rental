package pl.borowa5b.car.rental.domain.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.domain.DomainException
import pl.borowa5b.car.rental.domain.model.vo.CustomerId

class CustomerHasActiveRentalsException(customerId: CustomerId) :
    DomainException(Status.CONFLICT, "Customer with id ${customerId.value} has active rentals")