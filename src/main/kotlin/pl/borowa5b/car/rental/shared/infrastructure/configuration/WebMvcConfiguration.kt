package pl.borowa5b.car.rental.shared.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer = object : WebMvcConfigurer {

        override fun addCorsMappings(registry: CorsRegistry) {
            registry
                .addMapping("/**")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.POST.name())
        }
    }
}