package pl.borowa5b.car.rental.shared.helper

import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.RabbitMQContainer
import pl.borowa5b.car.rental.CarRentalApplication

@ActiveProfiles("test")
@Target(AnnotationTarget.CLASS)
@ExtendWith(IntegrationTestLifecycleManager::class)
@SpringBootTest(classes = [CarRentalApplication::class])
annotation class IntegrationTest

class IntegrationTestLifecycleManager : SpringExtension(), CloseableResource {

    companion object {

        private var areContainersRunning = false
    }

    private val postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16-alpine")
        .withDatabaseName("car-rental")
        .withUsername("postgres")
        .withPassword("postgres")

    private val rabbitMqContainer: RabbitMQContainer = RabbitMQContainer("rabbitmq")
        .withAdminUser("it")
        .withAdminPassword("it")

    override fun beforeAll(context: ExtensionContext) {
        if (!areContainersRunning) {
            areContainersRunning = true
            postgresContainer.start()
            rabbitMqContainer.start()
            setProperties()
        }
    }

    override fun beforeEach(context: ExtensionContext) =
        (getApplicationContext(context).getBean("database") as Database).prepare()


    override fun close() {
        if (areContainersRunning) {
            areContainersRunning = false
            postgresContainer.stop()
            rabbitMqContainer.stop()
        }
    }

    private fun setProperties() {
        setPostgresContainerProperties()
        setRabbitMqContainerProperties()
    }

    private fun setPostgresContainerProperties() {
        System.setProperty("spring.datasource.url", postgresContainer.jdbcUrl)
        System.setProperty("spring.datasource.username", postgresContainer.username)
        System.setProperty("spring.datasource.password", postgresContainer.password)
    }

    private fun setRabbitMqContainerProperties() {
        System.setProperty("spring.rabbitmq.host", rabbitMqContainer.host)
        System.setProperty("spring.rabbitmq.port", rabbitMqContainer.getMappedPort(5672).toString())
    }
}