package br.com.joaovq.data

import br.com.joaovq.data.models.Employers
import br.com.joaovq.data.models.Users
import com.market.core.utils.PropertiesConfigName
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import java.io.File

object DatabaseFactory {
    private val log = LoggerFactory.getLogger(this::class.java)
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
        val datasource =
            createHikariDataSource(url = jdbcURL, driver = driverClassName, user = user, password = password)

        runFlyway(datasource)
        val database = Database.connect(
            datasource
        )
        transaction(database) {
            addLogger(StdOutSqlLogger)
            /*SchemaUtils.create(Users)
            SchemaUtils.create(Employers)*/
        }
    }

    private fun runFlyway(datasource: HikariDataSource) {
        val flyway = Flyway.configure().baselineOnMigrate(true).dataSource(datasource).load()
        try {
            flyway.info()
            flyway.migrate()
        } catch (e: Exception) {
            log.error("Exception running flyway migration", e)
            throw e
        }
        log.info("Flyway migration has finished")
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