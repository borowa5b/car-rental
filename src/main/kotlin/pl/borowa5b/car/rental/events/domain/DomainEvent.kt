package pl.borowa5b.car.rental.events.domain

interface DomainEvent {

    fun getType(): String
    fun getVersion(): String
}