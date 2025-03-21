package pl.borowa5b.car.rental.shared.infrastructure.configuration

import com.fasterxml.jackson.core.StreamReadFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
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
            JavaTimeModule(),
            ProblemModule()
        )
            .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
}