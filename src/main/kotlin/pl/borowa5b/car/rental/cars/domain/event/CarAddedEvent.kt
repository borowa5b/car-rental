package pl.borowa5b.car.rental.cars.domain.event

import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.events.domain.DomainEvent
import java.math.BigDecimal

class CarAddedEvent(
    val id: String,
    val brand: String,
    val model: String,
    val generation: String,
    val productionYear: Int,
    val color: String,
    val pricePerDay: BigDecimal,
    val quantity: Int
) : DomainEvent {

    constructor(car: Car) : this(
        car.id.value,
        car.brand.name,
        car.model,
        car.generation,
        car.productionYear,
        car.color,
        car.pricePerDay,
        car.quantity
    )

    override fun getType(): String = "CarAdded"

    override fun getVersion(): String = "1.0"
}