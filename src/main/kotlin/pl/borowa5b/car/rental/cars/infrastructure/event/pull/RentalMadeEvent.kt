package pl.borowa5b.car.rental.cars.infrastructure.event.pull

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class RentalMadeEvent(val carId: String)