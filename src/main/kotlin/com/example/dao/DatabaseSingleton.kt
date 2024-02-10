package com.example.dao

import com.example.models.Finances
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*
import javax.sql.DataSource

object DatabaseSingleton {
  fun init(config: ApplicationConfig) {
    val dataSource = createHikariDataSource(
      config.property("database.jdbcURL").getString(),
      config.property("database.driverClassName").getString(),
      config.property("database.user").getString(),
      config.property("database.password").getString(),
    )

    val database = Database.connect(
      url = config.property("database.jdbcURL").getString(),
      driver = config.property("database.driverClassName").getString(),
      user = config.property("database.user").getString(),
      password = config.property("database.password").getString(),
    )

    initFlyway(dataSource)

    transaction(database) {
      SchemaUtils.create(Finances)
    }
  }

  private fun createHikariDataSource(
    _jdbcUrl: String,
    _driverClassName: String,
    _user: String,
    _password: String
  ) = HikariDataSource(HikariConfig().apply {
    driverClassName = _driverClassName
    jdbcUrl = _jdbcUrl
    maximumPoolSize = 3
    isAutoCommit = false
    username = _user
    password = _password
    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    validate()
  })

  private fun initFlyway(datasource: DataSource) {
    val flyway = Flyway.configure().dataSource(datasource).load()

    try {
      flyway.info()
      flyway.migrate()
    } catch (e: Exception) {
      print("Exception running flyway migration")
      throw e
    } finally {
      print("Flyway migration has finished")
    }
  }

  suspend fun <T>dbQuery(block: () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}