package com.example.core.utils

object PropertiesConfigName {
    const val KTOR_ENVIRON = "ktor.environment"

    object Jwt {
        const val JWT_SECRET = "jwt.secret"
        const val JWT_REALM = "jwt.realm"
        const val JWT_ISSUER = "jwt.issuer"
        const val JWT_AUDIENCE = "jwt.audience"
    }

    object Storage {
        const val STORAGE_DRIVER_CLASS_NAME = "storage.driverClassName"
        const val STORAGE_USER = "storage.user"
        const val STORAGE_PASSWORD = "storage.password"
        const val STORAGE_JDBC_URL = "storage.jdbcURL"
        const val STORAGE_DB_PATH = "storage.dbFilePath"
    }
}