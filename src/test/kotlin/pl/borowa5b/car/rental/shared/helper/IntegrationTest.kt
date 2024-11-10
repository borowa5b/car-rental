package pl.borowa5b.car.rental.shared.helper

import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer
import pl.borowa5b.car.rental.CarRentalApplication

@ActiveProfiles("test")
@Target(AnnotationTarget.CLASS)
@ExtendWith(IntegrationTestLifecycleManager::class)
@SpringBootTest(classes = [CarRentalApplication::class])
annotation class IntegrationTest

class IntegrationTestLifecycleManager : SpringExtension(), CloseableResource {

    companion object {

        private var hasDatabaseStarted = false
        private var hasDatabaseStopped = false
    }

    private val postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16-alpine")
        .withDatabaseName("car-rental")
        .withUsername("postgres")
        .withPassword("postgres")

    override fun beforeAll(context: ExtensionContext) {
        if (!hasDatabaseStarted) {
            hasDatabaseStarted = true
            postgresContainer.start()
            setProperties()
        }
    }

    override fun beforeEach(context: ExtensionContext) =
        (getApplicationContext(context).getBean("database") as Database).prepare()


    override fun close() {
        if (!hasDatabaseStopped) {
            hasDatabaseStopped = true
            postgresContainer.stop()
        }
    }

    private fun setProperties() {
        System.setProperty("spring.datasource.url", postgresContainer.jdbcUrl)
        System.setProperty("spring.datasource.username", postgresContainer.username)
        System.setProperty("spring.datasource.password", postgresContainer.password)
    }
}