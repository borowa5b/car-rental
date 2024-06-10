package pl.borowa5b.car.rental.customers.domain.command

data class SaveCustomerCommand(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val address: String,
    val documentNumber: String
)