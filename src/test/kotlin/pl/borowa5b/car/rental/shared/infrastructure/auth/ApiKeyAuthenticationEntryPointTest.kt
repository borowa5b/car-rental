package pl.borowa5b.car.rental.shared.infrastructure.auth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.zalando.problem.Problem
import org.zalando.problem.Status
import org.zalando.problem.jackson.ProblemModule

class ApiKeyAuthenticationEntryPointTest {

    @Test
    fun `should commence`() {
        // given
        val objectMapper = jacksonObjectMapper().apply {
            registerModules(ProblemModule())
        }
        val authenticationEntryPoint = ApiKeyAuthenticationEntryPoint(objectMapper)
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        val authException = object : AuthenticationException("auth exception message") {}

        // when
        authenticationEntryPoint.commence(request, response, authException)

        // then
        val thrownProblem = objectMapper.readValue(response.contentAsString, Problem::class.java)
        assertThat(thrownProblem.type.toString()).isEqualTo("car-rental/unauthorized")
        assertThat(thrownProblem.title).isEqualTo("Unauthorized")
        assertThat(thrownProblem.detail).isEqualTo("Access to this resource requires authorization")
        assertThat(thrownProblem.status).isEqualTo(Status.UNAUTHORIZED)
    }
}