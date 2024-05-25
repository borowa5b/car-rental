package pl.borowa5b.car.rental.infrastructure

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class RestErrorHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [IllegalArgumentException::class, IllegalStateException::class])
    fun handle(exception: RuntimeException, request: WebRequest): ResponseEntity<Any>? {
        val bodyOfResponse = """
            {
                "status": ${HttpStatus.CONFLICT.value()},
                "error": "Invalid request parameters",
                "message": "${exception.message}"
            }
        """.trimIndent()
        return handleExceptionInternal(
            exception, bodyOfResponse,
            HttpHeaders(), HttpStatus.CONFLICT, request
        )
    }
}