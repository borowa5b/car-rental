package pl.borowa5b.car.rental.rentals.domain.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.shared.domain.DomainException

class CustomerHasActiveRentalsException(customerId: CustomerId) :
    DomainException(Status.CONFLICT, "Customer with id ${customerId.value} has active rentals")