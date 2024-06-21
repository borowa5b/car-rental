package pl.borowa5b.car.rental.shared.infrastructure.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import java.util.logging.Logger

@Component
class RequestLoggingFilter : OncePerRequestFilter() {

    companion object {

        private val LOGGER = Logger.getLogger(RequestLoggingFilter::class.simpleName)
    }


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val wrappedRequest = ContentCachingRequestWrapper(request)
        filterChain.doFilter(wrappedRequest, response)

        val requestMessage = StringBuilder("Incoming request: ${wrappedRequest.method}.${wrappedRequest.requestURI}")
        wrappedRequest.getHeader("Role")?.let {
            requestMessage.append(" | Role: $it")
        }
        wrappedRequest.queryString?.let {
            requestMessage.append(" | Query params: $it")
        }
        val requestBody = getRequestBody(wrappedRequest)
        if (requestBody.isNotBlank()) {
            requestMessage.append(" | Request Body: $requestBody")
        }
        LOGGER.info(requestMessage.toString())
    }

    private fun getRequestBody(request: ContentCachingRequestWrapper): String =
        request.contentAsString.replace("\n", "").replace(Regex("\\s+"), " ")
}