package pl.borowa5b.car.rental.shared.infrastructure.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import jakarta.annotation.security.RolesAllowed
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.borowa5b.car.rental.shared.domain.vo.Role

@Configuration
class SwaggerConfiguration {

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(Info().title("Car rentals API").version("v1"))
        .servers(listOf(Server().apply { url = "/" }))
        .security(listOf(SecurityRequirement().addList("Authorization header")))
        .components(
            Components()
                .securitySchemes(
                    mapOf(
                        "Authorization header" to SecurityScheme()
                            .name("Bearer token")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .`in`(SecurityScheme.In.HEADER),
                    )
                )
        )

    @Bean
    fun allGroupApi(): GroupedOpenApi = GroupedOpenApi
        .builder()
        .group("ALL")
        .packagesToScan("pl.borowa5b.car.rental")
        .build()

    @Bean
    fun adminGroupApi(): GroupedOpenApi = GroupedOpenApi
        .builder()
        .group(Role.ROLE_ADMIN)
        .addOpenApiMethodFilter {
            val rolesAllowedAnnotation = it.annotations.firstOrNull { annotation -> annotation is RolesAllowed }
            rolesAllowedAnnotation != null && (rolesAllowedAnnotation as RolesAllowed).value.contains(Role.ADMIN)
        }
        .build()

    @Bean
    fun carsGroupApi(): GroupedOpenApi = GroupedOpenApi
        .builder()
        .group(Role.ROLE_CARS)
        .addOpenApiMethodFilter {
            val rolesAllowedAnnotation = it.annotations.firstOrNull { annotation -> annotation is RolesAllowed }
            rolesAllowedAnnotation != null && (rolesAllowedAnnotation as RolesAllowed).value.contains(Role.CARS)
        }
        .build()

    @Bean
    fun rentalsGroupApi(): GroupedOpenApi = GroupedOpenApi
        .builder()
        .group(Role.ROLE_RENTALS)
        .addOpenApiMethodFilter {
            val rolesAllowedAnnotation = it.annotations.firstOrNull { annotation -> annotation is RolesAllowed }
            rolesAllowedAnnotation != null && (rolesAllowedAnnotation as RolesAllowed).value.contains(Role.RENTALS)
        }
        .build()
}