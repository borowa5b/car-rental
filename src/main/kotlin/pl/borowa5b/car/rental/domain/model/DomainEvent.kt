package pl.borowa5b.car.rental.domain.model

interface DomainEvent {

    fun getType(): String
    fun getVersion(): String
}