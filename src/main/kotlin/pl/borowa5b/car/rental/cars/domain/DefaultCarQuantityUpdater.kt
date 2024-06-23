package pl.borowa5b.car.rental.cars.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.cars.domain.shared.CarQuantityUpdater
import pl.borowa5b.car.rental.cars.domain.shared.exception.CarNotFoundException
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId

@Component
class DefaultCarQuantityUpdater(private val carRepository: CarRepository) : CarQuantityUpdater {

    @Transactional
    override fun increase(carId: CarId) {
        val car = carRepository.findBy(carId) ?: throw CarNotFoundException(carId)
        car.increaseQuantity()
        carRepository.save(car)
    }

    @Transactional
    override fun decrease(carId: CarId) {
        val car = carRepository.findBy(carId) ?: throw CarNotFoundException(carId)
        car.decreaseQuantity()
        carRepository.save(car)
    }
}