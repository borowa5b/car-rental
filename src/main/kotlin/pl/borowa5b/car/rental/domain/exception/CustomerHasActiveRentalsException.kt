package pl.borowa5b.car.rental.domain.exception

import pl.borowa5b.car.rental.domain.DomainException
import pl.borowa5b.car.rental.domain.model.CustomerId

class CustomerHasActiveRentalsException(customerId: CustomerId) :
    DomainException("Customer with id ${customerId.value} has active rentals")