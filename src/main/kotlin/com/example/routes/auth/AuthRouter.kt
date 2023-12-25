package com.example.routes.auth

import com.example.core.security.BCryptPasswordHasher
import com.example.core.security.TokenFactory
import com.example.core.utils.PropertiesConfigName
import com.example.core.utils.exception.AuthorizationExceptionGroup
import com.example.core.utils.exception.BusinessExceptionGroup
import com.example.dao.user.UserRepository
import com.example.models.UserEntity
import com.example.plugins.AUTH_NAME
import com.example.domain.mapper.toUser
import com.example.domain.request.CreateUserRequest
import com.example.domain.request.LoginRequest
import com.example.domain.response.TokenResponse
import com.example.domain.usecases.auth.GetProfileDataUseCase
import com.example.domain.usecases.auth.SignInUseCase
import com.example.domain.usecases.auth.SignUpUserUseCase
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


@Resource("/token")
class Token(val sort: String? = "") {
    @Resource("register")
    class Register(val parent: Token = Token())

    @Resource("profile")
    class Profile(val parent: Token = Token())
}

fun Route.initializeAuthRouter(
    signInUseCase: SignInUseCase,
    signUpUserUseCase: SignUpUserUseCase,
    getProfileDataUseCase: GetProfileDataUseCase
) {
//    TODO Separate in functions for each route
    val secret = environment?.config?.property(PropertiesConfigName.Jwt.JWT_SECRET)?.getString()
    val issuer = environment?.config?.property(PropertiesConfigName.Jwt.JWT_ISSUER)?.getString()
    val audience = environment?.config?.property(PropertiesConfigName.Jwt.JWT_AUDIENCE)?.getString()
    val myRealm = environment?.config?.property(PropertiesConfigName.Jwt.JWT_REALM)?.getString()
    post<Token> { _ ->
        val user = call.receive<LoginRequest>()
        val userEntity = signInUseCase(user)
        val token = TokenFactory.genToken(audience, issuer, userEntity.toUser(), secret)
        call.respond(
            TokenResponse(
                token
            )
        )
    }

    post<Token.Register> { _ ->
        val request = call.receive<CreateUserRequest>()
        call.respond(
            HttpStatusCode.Created,
            mapOf(
                "user" to signUpUserUseCase(request)
            )
        )
    }

    authenticate(AUTH_NAME) {
        get<Token.Profile> {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim("username").asString()
            val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
            call.respond(
                HttpStatusCode.OK,
                getProfileDataUseCase(username) ?: throw BusinessExceptionGroup.UserNotFoundException()
            )
        }
    }
}


