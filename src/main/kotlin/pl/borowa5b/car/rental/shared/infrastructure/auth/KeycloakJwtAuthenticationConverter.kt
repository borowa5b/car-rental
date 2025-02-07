package pl.borowa5b.car.rental.shared.infrastructure.auth

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import pl.borowa5b.car.rental.shared.infrastructure.configuration.KeycloakConfiguration


class KeycloakJwtAuthenticationConverter(private val keycloakConfiguration: KeycloakConfiguration) :
    Converter<Jwt, AbstractAuthenticationToken> {

    override fun convert(source: Jwt): AbstractAuthenticationToken = JwtAuthenticationToken(
        source, extractResourceRoles(source)
    )

    private fun extractResourceRoles(jwt: Jwt): List<SimpleGrantedAuthority> {
        val resourceAccess = HashMap(jwt.getClaim<Map<String, List<String>>>("resource_access"))
        if (resourceAccess.isEmpty() || !resourceAccess.containsKey(keycloakConfiguration.clientId)) {
            return emptyList();
        }

        val realm = resourceAccess[keycloakConfiguration.clientId] as Map<*, *>
        val roles = realm["roles"] as ArrayList<*>
        return roles.stream()
            .map { role: Any -> SimpleGrantedAuthority("ROLE_" + (role as String).replace("-", "_")) }
            .toList()
    }
}