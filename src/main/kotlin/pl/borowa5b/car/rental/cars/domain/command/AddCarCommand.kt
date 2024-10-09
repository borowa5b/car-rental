package pl.borowa5b.car.rental.cars.domain.command

import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

data class AddCarCommand(
    val brand: Brand,
    private val model: String,
    private val generation: String,
    val productionYear: Int,
    private val color: String,
    val pricePerDay: BigDecimal,
    val quantity: Int
) {

    fun getModel(): String = model.lowercase()
    fun getGeneration(): String = generation.lowercase()
    fun getColor(): String = color.lowercase()
}
