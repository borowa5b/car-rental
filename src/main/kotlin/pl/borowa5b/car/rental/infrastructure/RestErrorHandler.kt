package pl.borowa5b.car.rental.infrastructure

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.zalando.problem.Problem
import org.zalando.problem.ProblemBuilder
import org.zalando.problem.Status
import pl.borowa5b.car.rental.domain.DomainException
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.domain.exception.ValidationException
import pl.borowa5b.car.rental.domain.exception.validation.ValidationError
import java.net.URI


@ControllerAdvice
class RestErrorHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [RuntimeException::class])
    fun handle(exception: RuntimeException): ResponseEntity<Problem> {
        val problem = Problem.builder()
            .withType(URI.create("car-rental/unexpected-error"))
            .withTitle("Unexpected error")
            .withDetail("Unexpected error occurred")
            .withStatus(Status.INTERNAL_SERVER_ERROR)
            .with("stacktrace", exception)
            .build()
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
            .withStatus(Status.CONFLICT)
            .build()
        return ResponseEntity.status(problem.status!!.statusCode).body(problem)
    }

    fun ProblemBuilder.with(validationErrors: List<ValidationError>): ProblemBuilder {
        with("errors", validationErrors)
        return this
    }
}