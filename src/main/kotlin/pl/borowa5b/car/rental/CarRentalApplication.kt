package pl.borowa5b.car.rental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.cars.domain.CarAdder
import pl.borowa5b.car.rental.cars.domain.command.AddCarCommand
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.customers.domain.CustomerSaver
import pl.borowa5b.car.rental.customers.domain.command.SaveCustomerCommand
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.RentalMaker
import pl.borowa5b.car.rental.rentals.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.rentals.domain.shared.repository.RentalRepository
import pl.borowa5b.car.rental.shared.domain.generator.IdGenerator
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.random.Random

@EnableScheduling
@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
class CarRentalApplication

fun main(args: Array<String>) {
    runApplication<CarRentalApplication>(*args)
}

@Component
class RentalsFiller(
    rentalMaker: RentalMaker,
    carAdder: CarAdder,
    idGenerator: IdGenerator,
    customerSaver: CustomerSaver,
    rentalRepository: RentalRepository
) {

    init {
        val carId = carAdder.add(AddCarCommand(Brand.FORD, "Mondeo", "MK3", 2006, "Metallic Black", BigDecimal(50), 10))
        for (i in 0 until 10) {
            val customerId = CustomerId(idGenerator.generate("CTR"))
            customerSaver.save(
                SaveCustomerCommand(
                    customerId.value,
                    "Jan",
                    "Kowalski",
                    "mail@mail.pl",
                    "231231532",
                    "bla street",
                    "DFA123532"
                )
            )
            val rentalId = rentalMaker.make(
                MakeRentalCommand(
                    carId,
                    customerId,
                    OffsetDateTime.now().minusDays(5),
                    OffsetDateTime.now().plusDays(10)
                )
            )
            if (Random.nextBoolean()) {
                rentalRepository.findById(rentalId)?.let {
                    it.start(OffsetDateTime.now(ZoneOffset.UTC))
                    rentalRepository.save(it)
                }
            }
        }
    }
}