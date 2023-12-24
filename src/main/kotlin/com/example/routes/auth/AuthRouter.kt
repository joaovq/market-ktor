package com.example.routes.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.core.utils.PropertiesConfigName
import com.example.dao.user.userDao
import com.example.schema.mapper.toEntity
import com.example.schema.request.CreateUserRequest
import com.example.schema.request.LoginRequest
import com.example.schema.response.TokenResponse
import io.ktor.http.*
import io.ktor.resources.Resource
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*


@Resource("/token")
class Token(val sort: String? = "") {
    @Resource("register")
    class Register(val parent: Token = Token())
}

fun Route.initializeAuthRouter() {
    val secret = environment?.config?.property(PropertiesConfigName.Jwt.JWT_SECRET)?.getString()
    val issuer = environment?.config?.property(PropertiesConfigName.Jwt.JWT_ISSUER)?.getString()
    val audience = environment?.config?.property(PropertiesConfigName.Jwt.JWT_AUDIENCE)?.getString()
    val myRealm = environment?.config?.property(PropertiesConfigName.Jwt.JWT_REALM)?.getString()
    post<Token> { _ ->
        val user = call.receive<LoginRequest>()
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .withExpiresAt(genExpiresTime())
            .sign(Algorithm.HMAC256(secret))
        call.respond(
            TokenResponse(
                token
            )
        )
    }

    post<Token.Register> { req ->
        val request = call.receive<CreateUserRequest>()
        val newUser = userDao.addNewUser(request.toEntity())
        call.respond(
            HttpStatusCode.Created,
            mapOf(
                "user" to newUser
            )
        )
    }
}

fun genExpiresTime(milliseconds: Int = 60000) =
    Date(System.currentTimeMillis() + milliseconds)
