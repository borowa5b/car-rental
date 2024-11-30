package pl.borowa5b.car.rental.shared.application.endpoint.dictionaries

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import pl.borowa5b.car.rental.shared.application.response.dictionaries.DictionaryResponse
import pl.borowa5b.car.rental.shared.domain.dictionaries.DictionariesResolver
import pl.borowa5b.car.rental.shared.domain.vo.Role

@DictionariesEndpoint
class GetDictionariesEndpoint {

    @RolesAllowed(value = [Role.ADMIN])
    @Operation(summary = "Gets dictionaries")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Dictionaries fetched successfully",
            content = [Content(schema = Schema(implementation = DictionaryResponse::class))]
        )
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getDictionaries(): List<DictionaryResponse> {
        return DictionariesResolver.resolve().map { entry ->
            DictionaryResponse(entry.key, entry.value.map { it.toString() })
        }
    }
}