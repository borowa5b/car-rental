package pl.borowa5b.car.rental.shared.infrastructure.auth

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import pl.borowa5b.car.rental.shared.infrastructure.configuration.KeycloakConfiguration

class KeycloakJwtAuthenticationConverterTest {

    private lateinit var keycloakConfiguration: KeycloakConfiguration
    private lateinit var converter: KeycloakJwtAuthenticationConverter
    private lateinit var jwt: Jwt

    @BeforeEach
    fun setUp() {
        keycloakConfiguration = mock(KeycloakConfiguration::class.java)
        `when`(keycloakConfiguration.clientId).thenReturn("test-client")
        converter = KeycloakJwtAuthenticationConverter(keycloakConfiguration)
        jwt = mock(Jwt::class.java)
    }

    @Test
    fun `should convert jwt token with correct authorities`() {
        // given
        val resourceAccess = mapOf(
            "test-client" to mapOf(
                "roles" to arrayListOf("role-1", "role-2")
            )
        )
        `when`(jwt.getClaim<Map<String, Any>>("resource_access")).thenReturn(resourceAccess)

        // when
        val result = converter.convert(jwt)

        // then
        assertThat(result)
            .isNotNull
            .isInstanceOf(JwtAuthenticationToken::class.java)
        assertThat(result.authorities)
            .contains(
                SimpleGrantedAuthority("ROLE_role_1"),
                SimpleGrantedAuthority("ROLE_role_2")
            )
            .hasSize(2)
    }

    @Test
    fun `should convert jwt token with empty roles`() {
        // given
        val resourceAccess = mapOf(
            "test-client" to mapOf(
                "roles" to arrayListOf<String>()
            )
        )
        `when`(jwt.getClaim<Map<String, Any>>("resource_access")).thenReturn(resourceAccess)

        // when
        val result = converter.convert(jwt)

        // then
        assertThat(result)
            .isNotNull
            .isInstanceOf(JwtAuthenticationToken::class.java)
        assertThat(result.authorities).isEmpty()
    }

    @Test
    fun `should convert jwt token with missing resource access`() {
        // given
        `when`(jwt.getClaim<Map<String, Any>>("resource_access")).thenReturn(emptyMap())

        // when
        val result = converter.convert(jwt)

        // then
        assertThat(result)
            .isNotNull
            .isInstanceOf(JwtAuthenticationToken::class.java)
        assertThat(result.authorities).isEmpty()
    }

    @Test
    fun `should convert jwt token with missing client id in resource access`() {
        // given
        val resourceAccess = mapOf(
            "other-client" to mapOf(
                "roles" to arrayListOf("role-1", "role-2")
            )
        )
        `when`(jwt.getClaim<Map<String, Any>>("resource_access")).thenReturn(resourceAccess)

        // when
        val result = converter.convert(jwt)

        // then
        assertThat(result)
            .isNotNull
            .isInstanceOf(JwtAuthenticationToken::class.java)
        assertThat(result.authorities).isEmpty()
    }

    @Test
    fun `should convert jwt token with roles with hyphens`() {
        // given
        val resourceAccess = mapOf(
            "test-client" to mapOf(
                "roles" to arrayListOf("role-with-hyphen")
            )
        )
        `when`(jwt.getClaim<Map<String, Any>>("resource_access")).thenReturn(resourceAccess)

        // when
        val result = converter.convert(jwt)

        // then
        assertThat(result)
            .isNotNull
            .isInstanceOf(JwtAuthenticationToken::class.java)
        assertThat(result.authorities)
            .contains(SimpleGrantedAuthority("ROLE_role_with_hyphen"))
            .hasSize(1)
    }
}