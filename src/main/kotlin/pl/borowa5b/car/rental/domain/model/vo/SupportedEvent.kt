package pl.borowa5b.car.rental.domain.model.vo

data class SupportedEvent(val type: String, val version: String, val payloadClass: Class<*>)