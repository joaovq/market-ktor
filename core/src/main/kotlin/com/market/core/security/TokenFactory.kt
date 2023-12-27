package com.market.core.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*
import java.util.concurrent.TimeUnit

const val CLAIM_NAME_USERNAME = "username"
const val CLAIM_NAME_ADMIN = "admin"

object TokenFactory {
    fun genToken(
        audience: String?,
        issuer: String?,
        username: String,
        isAdmin: Boolean,
        secret: String?
    ): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim(CLAIM_NAME_USERNAME, username)
        .withClaim(CLAIM_NAME_ADMIN, isAdmin)
        .withExpiresAt(genExpiresTime())
        .sign(Algorithm.HMAC256(secret))
}

private fun TokenFactory.genExpiresTime(
    minutes: Long = 10L
) = Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(minutes))