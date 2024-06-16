package pl.borowa5b.car.rental.shared.infrastructure.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import pl.borowa5b.car.rental.shared.infrastructure.auth.AuthenticationResolver

@Component
class AuthorizationFilter(private val authenticationResolver: AuthenticationResolver) : OncePerRequestFilter() {

    companion object {
        private const val AUTHORIZATION_HEADER = "authorization"
        private const val ROLE_HEADER = "ROLE"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeaderValue = request.getHeader(AUTHORIZATION_HEADER)
        val roleHeaderValue = request.getHeader(ROLE_HEADER)
        SecurityContextHolder.getContext().authentication =
            authenticationResolver.resolve(authorizationHeaderValue, roleHeaderValue)
        filterChain.doFilter(request, response)
    }
}