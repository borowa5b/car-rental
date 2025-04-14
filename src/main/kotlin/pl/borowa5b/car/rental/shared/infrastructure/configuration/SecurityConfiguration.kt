package pl.borowa5b.car.rental.shared.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import pl.borowa5b.car.rental.shared.infrastructure.auth.KeycloakJwtAuthenticationConverter

@Profile("!dev")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
class SecurityConfiguration(private val keycloakConfiguration: KeycloakConfiguration) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests { httpsRequests ->
            httpsRequests
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
        }
        .oauth2ResourceServer {
            it.jwt { jwt ->
                jwt.jwtAuthenticationConverter(
                    KeycloakJwtAuthenticationConverter(
                        keycloakConfiguration
                    )
                )
            }
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .build()
}

@Profile("dev")
@Configuration
class DevSecurityConfiguration {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests { httpsRequests ->
            httpsRequests.anyRequest().permitAll()
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .build()
}