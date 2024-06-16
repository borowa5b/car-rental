package pl.borowa5b.car.rental.shared.infrastructure.auth

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.shared.domain.vo.Role

class ApiKeyAuthenticationTest {

    @Test
    fun `should create authentication`() {
        // given
        val apiKey = "api-key"
        val authorities = listOf(Role.ROLE_ADMIN)

        // when
        val result = ApiKeyAuthentication(apiKey, authorities)

        // then
        assertThat(result.isAuthenticated).isTrue()
        assertThat(result.principal).isEqualTo("Api key authentication")
        assertThat(result.credentials).isEqualTo(apiKey)
        assertThat(result.authorities.map { it.authority }).contains(Role.ROLE_ADMIN)
    }
}