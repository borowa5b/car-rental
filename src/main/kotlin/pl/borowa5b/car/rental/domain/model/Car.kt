package pl.borowa5b.car.rental.domain.model

data class Car(
    val id: CarId,
    val brand: String,
    val model: String,
    val generation: String,
    val year: Int,
    val color: String,
    val pricePerDay: Int,
    val quantity: Int,
    val version: Long
)