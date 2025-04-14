package pl.borowa5b.car.rental.shared.infrastructure.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.SimpleMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix = "car-rental", name = ["events-queue-provider"], havingValue = "rabbitmq")
class RabbitMqConfiguration(
    @Value("\${spring.rabbitmq.exchange}") private val exchange: String,
    @Value("\${spring.rabbitmq.routing-key}") private val routingKey: String,
    @Value("\${spring.rabbitmq.queue}") private val queue: String
) {

    @Bean
    fun rentalsExchange(): DirectExchange = DirectExchange(exchange)

    @Bean
    fun rentalsBinding(): Binding =
        BindingBuilder.bind(rentalsQueue()).to(rentalsExchange()).with(routingKey)

    @Bean
    fun rentalsQueue(): Queue = Queue(queue)

    @Bean
    fun messageConverter(): SimpleMessageConverter = SimpleMessageConverter().apply {
        setAllowedListPatterns(listOf("pl.borowa5b.car.rental.*", "java.lang.*"))
    }
}