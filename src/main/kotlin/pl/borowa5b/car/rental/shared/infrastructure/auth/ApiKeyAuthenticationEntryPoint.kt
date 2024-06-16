package pl.borowa5b.car.rental.shared.infrastructure.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.zalando.problem.Problem
import org.zalando.problem.Status
import java.net.URI

@Component
class ApiKeyAuthenticationEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val responseWriter = response.writer
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val responseBody = objectMapper.writeValueAsString(
            Problem.builder()
                .withType(URI.create("car-rental/unauthorized"))
                .withTitle("Unauthorized")
                .withDetail("Access to this resource requires authorization")
                .withStatus(Status.UNAUTHORIZED)
                .build()
        )
        responseWriter.write(responseBody)
        responseWriter.flush()
        responseWriter.close()
    }
}