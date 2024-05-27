package pl.borowa5b.car.rental.infrastructure.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.problem.jackson.ProblemModule

@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun objectMapper() = ObjectMapper().apply {
        registerModules(
            KotlinModule.Builder().build(),
            ProblemModule()
        )
    }
}