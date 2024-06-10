package pl.borowa5b.car.rental.shared.domain

import org.zalando.problem.StatusType

open class DomainException(val status: StatusType, message: String) : RuntimeException(message)