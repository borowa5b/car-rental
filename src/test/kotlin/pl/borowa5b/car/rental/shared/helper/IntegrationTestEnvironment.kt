package pl.borowa5b.car.rental.shared.helper

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class IntegrationTestEnvironment : ApplicationContextAware {

    companion object {

        private lateinit var applicationContext: ApplicationContext

        fun <T> getBean(type: Class<T>): T = applicationContext.getBean(type)
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        IntegrationTestEnvironment.applicationContext = applicationContext
    }
}