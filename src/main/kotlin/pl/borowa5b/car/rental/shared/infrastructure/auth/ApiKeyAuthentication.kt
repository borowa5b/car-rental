package pl.borowa5b.car.rental.shared.infrastructure.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils

class ApiKeyAuthentication(private val apiKey: String, authorities: List<String>) :
    AbstractAuthenticationToken(AuthorityUtils.createAuthorityList(authorities)) {

    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any = apiKey

    override fun getPrincipal(): Any = "Api key authentication"
}