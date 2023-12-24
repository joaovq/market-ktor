package com.example.core.utils

object PropertiesConfigName {
    const val KTOR_ENVIRON = "ktor.environment"

    object Jwt {
        const val JWT_SECRET = "jwt.secret"
        const val JWT_REALM = "jwt.realm"
        const val JWT_ISSUER = "jwt.issuer"
        const val JWT_AUDIENCE = "jwt.audience"
    }
}