package pl.borowa5b.car.rental.domain.generator

interface IdGenerator {

    fun generate(prefix: String): String
}