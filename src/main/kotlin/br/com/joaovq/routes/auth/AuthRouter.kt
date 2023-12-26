package br.com.joaovq.routes.auth

import br.com.joaovq.domain.mapper.toUser
import br.com.joaovq.domain.request.CreateUserRequest
import br.com.joaovq.domain.request.LoginRequest
import br.com.joaovq.domain.response.TokenResponse
import br.com.joaovq.domain.usecases.auth.GetProfileDataUseCase
import br.com.joaovq.domain.usecases.auth.SignInUseCase
import br.com.joaovq.domain.usecases.auth.SignUpUserUseCase
import br.com.joaovq.plugins.AUTH_NAME
import com.market.core.security.TokenFactory
import com.market.core.utils.PropertiesConfigName
import com.market.core.utils.exception.BusinessExceptionGroup
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
        val token = TokenFactory.genToken(audience, issuer, userEntity.username, secret)
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


