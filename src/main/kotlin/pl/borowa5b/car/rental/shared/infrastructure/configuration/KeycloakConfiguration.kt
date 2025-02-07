package pl.borowa5b.car.rental.shared.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "keycloak")
class KeycloakConfiguration {

    lateinit var clientId: String
}