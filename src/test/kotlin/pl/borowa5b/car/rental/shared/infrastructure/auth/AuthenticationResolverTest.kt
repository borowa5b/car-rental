package pl.borowa5b.car.rental.shared.infrastructure.auth

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.shared.domain.vo.Role

class AuthenticationResolverTest {

    @Test
    fun `should resolve`() {
        // given
        val authenticationResolver = AuthenticationResolver("api-key")

        // when
        val result = authenticationResolver.resolve("api-key", Role.ROLE_ADMIN)!!

        // then
        assertThat(result).isExactlyInstanceOf(ApiKeyAuthentication::class.java)
        assertThat(result.isAuthenticated).isTrue()
        assertThat(result.principal).isEqualTo("Api key authentication")
        assertThat(result.credentials).isEqualTo("api-key")
        assertThat(result.authorities.map { it.authority }).contains(Role.ROLE_ADMIN)
    }

    @Test
    fun `should not resolve`() {
        // given
        val authenticationResolver = AuthenticationResolver("api-key")

        // when
        val result = authenticationResolver.resolve("wrong-api-key", Role.ROLE_ADMIN)

        // then
        assertThat(result).isNull()
    }
}