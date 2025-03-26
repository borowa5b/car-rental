import org.flywaydb.gradle.task.AbstractFlywayTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun
import java.util.Properties

val flywayVersion = rootProject.extra["flywayVersion"]

plugins {
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.10.6"
    id("org.jetbrains.kotlin.plugin.jpa") version "2.1.10"
    id("org.flywaydb.flyway") version "11.4.0"
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.spring") version "2.1.20"
}

group = "pl.borowa5b"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

buildscript {
    extra.set("flywayVersion", "11.4.0")

    dependencies {
        val flywayVersion = rootProject.extra.get("flywayVersion")
        classpath("org.flywaydb:flyway-database-postgresql:$flywayVersion")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // SPRING
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // JACKSON
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // EXCEPTIONS
    implementation("org.zalando:problem:0.27.1")
    implementation("org.zalando:jackson-datatype-problem:0.27.1")

    // SWAGGER
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")

    // DB
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")

    // OTHERS
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("io.viascom.nanoid:nanoid:1.0.1")

    // TESTS
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation("com.tngtech.archunit:archunit-junit5:1.4.0")
    testImplementation("io.github.hakky54:logcaptor:2.10.1")
    testImplementation("org.testcontainers:postgresql:1.20.6")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

// Disable generating plain archive jar
tasks.named<Jar>("jar") {
    enabled = false
}

// Enable jUnit
tasks.withType<Test> {
    useJUnitPlatform()
}

// Run application with dev profile on local machine
tasks.named<BootRun>("bootRun") {
    if (System.getProperty("spring.profiles.active") == null) {
        systemProperty("spring.profiles.active", "dev")
    }
}

// Add dependency to process resources before running flyway task so flyway recognizes changes in migration files
tasks.withType<AbstractFlywayTask> {
    dependsOn("processResources")
}

flyway {
    val applicationProperties = Properties().apply {
        load(file("src/main/resources/application-dev.properties").inputStream())
    }

    url = applicationProperties.getProperty("spring.datasource.url")
    user = applicationProperties.getProperty("spring.datasource.username")
    password = applicationProperties.getProperty("spring.datasource.password")
    locations = arrayOf("classpath:changesets")
    cleanDisabled = false
}