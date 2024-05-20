package pl.borowa5b.car.rental.infrastructure.generator

import io.viascom.nanoid.NanoId
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.generator.IdGenerator

@Component
class DefaultIdGenerator : IdGenerator {

    override fun generate(prefix: String): String = "$prefix${NanoId.generate(alphabet = ID_ALPHABET)}"

    companion object {

        private const val ID_ALPHABET = "0123456789"
    }
}