package pl.borowa5b.car.rental.shared.infrastructure.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class TrailingSlashRedirectFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val httpServletRequest = request as HttpServletRequest
        val path = httpServletRequest.requestURI
        val forwardedRequest = if (path.endsWith("/"))
            object : HttpServletRequestWrapper(request) {
                override fun getRequestURI(): String = path.trimEnd('/')
            }
        else request
        filterChain.doFilter(forwardedRequest, response)
    }
}