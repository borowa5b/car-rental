package pl.borowa5b.car.rental.cars.domain.shared.model

import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

object DomainObjects {

    fun car(
        id: CarId = carId(),
        brand: Brand = Brand.TOYOTA,
        model: String = "orolla",
        generation: String = "le",
        productionYear: Int = 2022,
        color: String = "blue",
        pricePerDay: BigDecimal = BigDecimal(200),
        quantity: Int = 5
    ): Car = Car(
        id = id,
        brand = brand,
        model = model,
        generation = generation,
        productionYear = productionYear,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity,
        version = 0
    )
}