package pl.borowa5b.car.rental.shared.infrastructure.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.shared.domain.vo.Role

@Component
class AuthenticationResolver(@Value("\${car-rental.api-key}") private val apiKey: String) {

    fun resolve(authorizationHeaderValue: String?, roleHeaderValue: String?): Authentication? =
        authorizationHeaderValue?.let {
            val role = resolveRoles(roleHeaderValue)
            return if (authorizationHeaderValue == apiKey) ApiKeyAuthentication(apiKey, role) else null
        }

    private fun resolveRoles(roleHeaderValue: String?): List<String> =
        if (Role.allRoles().contains(roleHeaderValue)) listOf(roleHeaderValue!!) else emptyList()
}