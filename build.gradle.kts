val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val hikaricp_version: String by project
val ehcache_version: String by project
val koin_ktor: String by project
val koin_version: String by project
val prometheus_version: String by project
val koin_ksp_version: String by project
val koin_annotations_version: String by project

plugins {
    kotlin("jvm") version "1.9.20"
    id("io.ktor.plugin") version "2.3.6"
    application
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.20"
    id("com.google.devtools.ksp") version "1.9.22-1.0.16"
}

group = "br.com.joaovq"
version = "0.0.1"

application {
    mainClass.set("${group}.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

val exposed_version: String by project
val h2_version: String by project
val swagger_codegen_version: String by project

dependencies {
    implementation(project("core"))

    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-swagger-jvm")
//    Type safety
    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
//    Exposed - Database ORM
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("com.h2database:h2:$h2_version")

    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-openapi:$ktor_version")
    implementation("io.swagger.codegen.v3:swagger-codegen-generators:$swagger_codegen_version")

//    Error handler
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

//    Validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
//    Encrypt password
// https://mvnrepository.com/artifact/at.favre.lib/bcrypt
    implementation("at.favre.lib:bcrypt:0.10.2")

//    Migrations
    implementation("org.flywaydb:flyway-core:10.0.0")

//    Caching db and pooling
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("org.ehcache:ehcache:$ehcache_version")

//    Koin dependency injection
    implementation("io.insert-koin:koin-core:$koin_version")
    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    implementation("io.insert-koin:koin-annotations:${koin_annotations_version}")
    ksp("io.insert-koin:koin-ksp-compiler:${koin_annotations_version}")
    // SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor")

    // Koin Test features
    testImplementation("io.insert-koin:koin-test:$koin_version")
    // Koin for JUnit 5
    testImplementation("io.insert-koin:koin-test-junit5:$koin_version")

    implementation("io.ktor:ktor-server-metrics-micrometer:$ktor_version")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometheus_version")

//    Ktor Client
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    /*Logging*/
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    /*Serialization*/
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
//    Test
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
}


