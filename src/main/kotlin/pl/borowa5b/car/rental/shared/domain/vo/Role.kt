package pl.borowa5b.car.rental.shared.domain.vo

object Role {
    const val ADMIN = "ADMIN"
    const val USER = "USER"

    const val ROLE_ADMIN = "ROLE_ADMIN"
    const val ROLE_USER = "ROLE_USER"

    fun allRoles(): List<String> = listOf(ROLE_ADMIN, ROLE_USER)
}