package pl.borowa5b.car.rental.events.domain.shared.vo

data class SupportedEvent(val type: String, val version: String, val payloadClass: Class<*>)