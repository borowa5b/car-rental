package pl.borowa5b.car.rental.shared.domain.generator

interface IdGenerator {

    fun generate(prefix: String): String
}