package pl.borowa5b.car.rental.shared.infrastructure

import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.zalando.problem.Problem
import org.zalando.problem.ProblemBuilder
import org.zalando.problem.Status
import pl.borowa5b.car.rental.shared.domain.DomainException
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.exception.ValidationException
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationError
import java.net.URI
import java.util.logging.Logger


@ControllerAdvice
class RestErrorHandler(private val logger: Logger = Logger.getLogger(RestErrorHandler::class.simpleName)) {

    @ExceptionHandler(value = [Exception::class])
    fun handle(exception: Exception): ResponseEntity<Problem> {
        val status = resolveStatus(exception)
        val message = resolveMessage(exception)
        val problem = Problem.builder()
            .withType(URI.create("car-rental/unexpected-error"))
            .withTitle("Unexpected error")
            .withDetail(message)
            .withStatus(status)
            .build()
        logger.warning("Unexpected error occurred: $exception")
        return ResponseEntity.status(problem.status!!.statusCode).body(problem)
    }

    @ExceptionHandler(value = [ValidationErrorException::class])
    fun handle(exception: ValidationErrorException): ResponseEntity<Problem> {
        val error = exception.error
        val problem = Problem.builder()
            .withType(URI.create("car-rental/validation-error"))
            .withTitle(error.title)
            .withDetail(error.message)
            .withStatus(Status.BAD_REQUEST)
            .build()
        return ResponseEntity.status(problem.status!!.statusCode).body(problem)
    }

    @ExceptionHandler(value = [ValidationException::class])
    fun handle(exception: ValidationException): ResponseEntity<Problem> {
        val problem = Problem.builder()
            .withType(URI.create("car-rental/validation-errors"))
            .withTitle("Validation errors")
            .withDetail("Validation errors occurred")
            .withStatus(Status.BAD_REQUEST)
            .with(exception.errors)
            .build()
        return ResponseEntity.status(problem.status!!.statusCode).body(problem)
    }

    @ExceptionHandler(value = [DomainException::class])
    fun handle(exception: DomainException): ResponseEntity<Problem> {
        val problem = Problem.builder()
            .withType(URI.create("car-rental/business-error"))
            .withTitle("Business error")
            .withDetail(exception.message)
            .withStatus(exception.status)
            .build()
        logger.warning("Business error occurred: $exception")
        return ResponseEntity.status(problem.status!!.statusCode).body(problem)
    }

    private fun ProblemBuilder.with(validationErrors: List<ValidationError>): ProblemBuilder {
        with("errors", validationErrors)
        return this
    }

    private fun resolveMessage(exception: Exception): String? = when (exception) {
        is HttpMediaTypeException -> exception.body.detail
        else -> exception.message
    }

    private fun resolveStatus(exception: Exception): Status = when (exception) {
        is HttpMediaTypeException -> Status.valueOf(exception.body.status)
        else -> Status.INTERNAL_SERVER_ERROR
    }
}