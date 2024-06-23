package pl.borowa5b.car.rental.shared.helper

import pl.borowa5b.car.rental.cars.infrastructure.entity.EntityObjects.carEntity
import pl.borowa5b.car.rental.customers.infrastructure.entity.EntityObjects.customerEntity

object IntegrationTestEntityCreator {

    private val carRepository = IntegrationTestEnvironment.getBean(TestSpringCarRepository::class.java)
    private val customerRepository = IntegrationTestEnvironment.getBean(TestSpringCustomerRepository::class.java)

    fun createCarEntity(id: String, quantity: Int = 10) {
        carRepository.save(carEntity(id = id, quantity = quantity))
    }

    fun createCustomerEntity(id: String) {
        customerRepository.save(customerEntity(id = id))
    }
}