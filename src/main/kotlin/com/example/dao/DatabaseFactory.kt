package com.example.dao

import com.example.core.utils.PropertiesConfigName
import com.example.models.Employers
import com.example.models.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.client.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object DatabaseFactory {
    fun init(
        config: ApplicationConfig
    ) {
        val driverClassName = config.property(PropertiesConfigName.Storage.STORAGE_DRIVER_CLASS_NAME).getString()
        val user = config.property(PropertiesConfigName.Storage.STORAGE_USER).getString()
        val password = config.property(PropertiesConfigName.Storage.STORAGE_PASSWORD).getString()
        val jdbcURL = config.property(PropertiesConfigName.Storage.STORAGE_JDBC_URL).getString() +
                (config.propertyOrNull(PropertiesConfigName.Storage.STORAGE_DB_PATH)?.getString()?.let {
                    File(it).canonicalFile.absolutePath
                } ?: "")
        val datasource = createHikariDataSource(url = jdbcURL, driver = driverClassName, user, password)

        /*val flyway = Flyway.configure().dataSource(datasource).load()
        flyway.migrate()*/
        val database = Database.connect(
            datasource
        )
//        TODO fix flyway migrate
        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Users)
            SchemaUtils.create(Employers)
        }
    }

    private fun createHikariDataSource(
        url: String,
        driver: String,
        user: String,
        password: String
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        username = user
        setPassword(password)
        validate()
    })

    private fun enableCache() {
        TODO("Implements cache")
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}