package pl.borowa5b.car.rental.infrastructure.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.springframework.stereotype.Component

@Component
class TrailingSlashRedirectFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest
        val path = httpServletRequest.requestURI
        val forwardedRequest = if (path.endsWith("/"))
            object : HttpServletRequestWrapper(request) {
                override fun getRequestURI(): String = path.trimEnd('/')
            }
        else request
        chain.doFilter(forwardedRequest, response)
    }
}