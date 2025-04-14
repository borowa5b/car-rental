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
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import pl.borowa5b.car.rental.shared.domain.vo.Role

@Configuration
class SwaggerConfiguration(private val environment: Environment) {

    @Bean
    fun openApi(): OpenAPI {
        val openApi = OpenAPI()
        if (!environment.acceptsProfiles(Profiles.of("dev"))) {
            openApi
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
        }
        return openApi
            .info(Info().title("Car rentals API").version("v1"))
            .servers(listOf(Server().apply { url = "/" }))
    }

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