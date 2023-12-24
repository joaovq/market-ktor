package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.core.utils.PropertiesConfigName
import com.example.core.utils.exception.AuthorizationExceptionGroup
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

const val AUTH_NAME = "auth-jwt"

fun Application.configureSecurity() {
    val secret = environment.config.property(PropertiesConfigName.Jwt.JWT_SECRET).getString()
    val issuer = environment.config.property(PropertiesConfigName.Jwt.JWT_ISSUER).getString()
    val audience = environment.config.property(PropertiesConfigName.Jwt.JWT_AUDIENCE).getString()
    val myRealm = environment.config.property(PropertiesConfigName.Jwt.JWT_REALM).getString()
    authentication {
        jwt(AUTH_NAME) {
            realm = myRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(audience)) JWTPrincipal(credential.payload) else null
            }
            challenge { defaultScheme, realm ->
                throw AuthorizationExceptionGroup.AuthorizationException()
            }
        }
    }
}
