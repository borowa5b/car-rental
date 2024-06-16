package pl.borowa5b.car.rental.shared.infrastructure.filter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.infrastructure.auth.AuthenticationResolver

class AuthorizationFilterTest {

    @BeforeEach
    fun `before each`() {
        SecurityContextHolder.setContext(SecurityContextImpl())
    }

    @AfterEach
    fun `after each`() {
        SecurityContextHolder.clearContext()
    }

    @Test
    fun `should set authentication`() {
        // given
        val apiKey = "123"
        val authenticationResolver = AuthenticationResolver(apiKey)
        val filter = AuthorizationFilter(authenticationResolver)
        val request = MockHttpServletRequest().apply {
            addHeader("Authorization", apiKey)
            addHeader("Role", Role.ROLE_ADMIN)
        }
        val response = MockHttpServletResponse()
        val chain = MockFilterChain()

        // when
        filter.doFilter(request, response, chain)

        // then
        val authentication = SecurityContextHolder.getContext().authentication
        assertThat(authentication.isAuthenticated).isTrue()
        assertThat(authentication.authorities.map { it.authority }).contains(Role.ROLE_ADMIN)
    }

    @Test
    fun `should not set authentication`() {
        // given
        val authenticationResolver = AuthenticationResolver("123")
        val filter = AuthorizationFilter(authenticationResolver)
        val request = MockHttpServletRequest().apply {
            addHeader("Authorization", "wrongApiKey")
            addHeader("Role", Role.ROLE_ADMIN)
        }
        val response = MockHttpServletResponse()
        val chain = MockFilterChain()

        // when
        filter.doFilter(request, response, chain)

        // then
        val authentication = SecurityContextHolder.getContext().authentication
        assertThat(authentication).isNull()
    }
}