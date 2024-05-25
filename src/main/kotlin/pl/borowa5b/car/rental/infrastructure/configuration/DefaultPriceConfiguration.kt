package pl.borowa5b.car.rental.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import pl.borowa5b.car.rental.domain.PriceConfiguration
import java.math.BigDecimal

@Configuration
@ConfigurationProperties(prefix = "car-rental")
class DefaultPriceConfiguration : PriceConfiguration {

    override lateinit var pricePerDay: BigDecimal
}