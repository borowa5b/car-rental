package pl.borowa5b.car.rental.shared.infrastructure.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.logging.Logger

@Component
class RequestLoggingFilter : OncePerRequestFilter() {

    companion object {

        private val LOGGER = Logger.getLogger(RequestLoggingFilter::class.simpleName);
    }


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestMessage = StringBuilder("Incoming request: ${request.method}.${request.requestURI}")
        request.getHeader("Role")?.let {
            requestMessage.append(" | Role: $it")
        }
        request.queryString?.let {
            requestMessage.append(" | Query params: $it")
        }
        val requestBody = getRequestBody(request)
        if (requestBody.isNotBlank()) {
            requestMessage.append(" | Request Body: $requestBody")
        }
        LOGGER.info(requestMessage.toString())
        filterChain.doFilter(request, response)
    }

    private fun getRequestBody(request: HttpServletRequest): String = String(
        request.inputStream.readAllBytes()
    ).replace("\n", "").replace(Regex("\\s+"), " ")
}