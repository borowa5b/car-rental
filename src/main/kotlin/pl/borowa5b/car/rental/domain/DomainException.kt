package pl.borowa5b.car.rental.domain

import org.zalando.problem.StatusType

open class DomainException(val status: StatusType, message: String) : RuntimeException(message)