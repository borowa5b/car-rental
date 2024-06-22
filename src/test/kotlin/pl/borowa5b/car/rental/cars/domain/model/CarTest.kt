package pl.borowa5b.car.rental.cars.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.command.EditCarCommand
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car
import java.math.BigDecimal

class CarTest {

    @Test
    fun `should not edit`() {
        // given
        val car = car()
        val command = EditCarCommand(
            carId = car.id,
            brand = car.brand,
            model = car.model,
            generation = car.generation,
            year = car.year,
            color = car.color,
            pricePerDay = car.pricePerDay,
            quantity = car.quantity
        )
        // when
        val result = car.edit(command)

        // then
        assertThat(result).isFalse()
    }

    @Test
    fun `should edit`() {
        // given
        val car = car(pricePerDay = BigDecimal(100))
        val command = EditCarCommand(
            carId = car.id,
            brand = car.brand,
            model = car.model,
            generation = car.generation,
            year = car.year,
            color = car.color,
            pricePerDay = BigDecimal(99),
            quantity = car.quantity
        )
        // when
        val result = car.edit(command)

        // then
        assertThat(result).isTrue()
    }
}