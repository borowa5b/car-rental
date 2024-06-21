package pl.borowa5b.car.rental

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@AnalyzeClasses(packagesOf = [CarRentalApplication::class])
class CarRentalsArchitectureTest {

    companion object {

        private const val APP_PACKAGE = "pl.borowa5b.car.rental"
        private const val CARS_DOMAIN_PACKAGE = "$APP_PACKAGE.cars.."
        private const val CUSTOMERS_DOMAIN_PACKAGE = "$APP_PACKAGE.customers.."
        private const val RENTALS_DOMAIN_PACKAGE = "$APP_PACKAGE.rentals.."
        private const val EVENTS_DOMAIN_PACKAGE = "$APP_PACKAGE.events.."
        private const val SHARED_DOMAIN_PACKAGE = "$APP_PACKAGE.shared.."
        private const val DOMAIN_PACKAGES = "..domain.."
        private const val INFRASTRUCTURE_PACKAGES = "..infrastructure.."
        private const val SHARED_PACKAGES = "..shared.."
        private const val LIBRARY_PACKAGES = "[java..|jakarta..|com..|org..|kotlin..|io..|nl..]"
    }

    @ArchTest
    private val `domain should not access infrastructure` = noClasses()
        .that()
        .resideInAPackage(DOMAIN_PACKAGES)
        .should()
        .accessClassesThat()
        .resideInAPackage(INFRASTRUCTURE_PACKAGES)

    @ArchTest
    private val `cars domain should not access other domains` = classes()
        .that()
        .resideInAPackage(CARS_DOMAIN_PACKAGE)
        .and()
        .haveSimpleNameNotEndingWith("BeanFactoryRegistrations")
        .should()
        .onlyAccessClassesThat()
        .resideInAnyPackage(CARS_DOMAIN_PACKAGE, SHARED_PACKAGES, LIBRARY_PACKAGES)

    @ArchTest
    private val `customers domain should not access other domains` = classes()
        .that()
        .resideInAPackage(CUSTOMERS_DOMAIN_PACKAGE)
        .and()
        .haveSimpleNameNotEndingWith("BeanFactoryRegistrations")
        .should()
        .onlyAccessClassesThat()
        .resideInAnyPackage(CUSTOMERS_DOMAIN_PACKAGE, SHARED_PACKAGES, LIBRARY_PACKAGES)

    @ArchTest
    private val `rentals domain should not access other domains` = classes()
        .that()
        .resideInAPackage(RENTALS_DOMAIN_PACKAGE)
        .should()
        .onlyAccessClassesThat()
        .resideInAnyPackage(RENTALS_DOMAIN_PACKAGE, SHARED_PACKAGES, LIBRARY_PACKAGES)

    @ArchTest
    private val `events domain should not access other domains` = classes()
        .that()
        .resideInAPackage(EVENTS_DOMAIN_PACKAGE)
        .should()
        .onlyAccessClassesThat()
        .resideInAnyPackage(EVENTS_DOMAIN_PACKAGE, SHARED_PACKAGES, LIBRARY_PACKAGES)

    @ArchTest
    private val `shared domain should not access other domains` = classes()
        .that()
        .resideInAPackage(SHARED_DOMAIN_PACKAGE)
        .and()
        .resideOutsideOfPackage("$APP_PACKAGE.shared.helper..")
        .should()
        .onlyAccessClassesThat()
        .resideInAnyPackage(SHARED_DOMAIN_PACKAGE, LIBRARY_PACKAGES)
}