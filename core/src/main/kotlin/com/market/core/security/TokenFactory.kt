package com.market.core.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object TokenFactory {
    fun genToken(
        audience: String?,
        issuer: String?,
        username: String,
        secret: String?
    ): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("username", username)
        .withExpiresAt(genExpiresTime())
        .sign(Algorithm.HMAC256(secret))
}

private fun TokenFactory.genExpiresTime(
    milliseconds: Int = 60000
) = Date(System.currentTimeMillis() + milliseconds)