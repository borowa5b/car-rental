package pl.borowa5b.car.rental.cars.domain.model

import pl.borowa5b.car.rental.cars.domain.command.EditCarCommand
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

data class Car(
    val id: CarId,
    var brand: Brand,
    var model: String,
    var generation: String,
    var year: Int,
    var color: String,
    var pricePerDay: BigDecimal,
    var quantity: Int,
    val version: Long = 0
) {

    fun edit(command: EditCarCommand): Boolean {
        val edited = ArrayList<Boolean>()
        command.brand?.let { edited.add(update(brand, it) { brand = it }) }
        command.model?.let { edited.add(update(model, it) { model = it }) }
        command.generation?.let { edited.add(update(generation, it) { generation = it }) }
        command.year?.let { edited.add(update(year, it) { year = it }) }
        command.color?.let { edited.add(update(color, it) { color = it }) }
        command.pricePerDay?.let { edited.add(update(pricePerDay, it) { pricePerDay = it }) }
        command.quantity?.let { edited.add(update(quantity, it) { quantity = it }) }
        return edited.any { it }
    }

    fun increaseQuantity() {
        quantity++
    }

    fun decreaseQuantity() {
        if (quantity == 0) {
            throw IllegalStateException("Quantity cannot be less than 0.")
        }
        quantity--
    }

    private fun update(oldValue: Any, newValue: Any, function: () -> Unit): Boolean {
        return if (oldValue != newValue) {
            function()
            true
        } else false
    }
}