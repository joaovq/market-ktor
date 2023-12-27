package br.com.joaovq.routes.user

import br.com.joaovq.domain.mapper.toUser
import br.com.joaovq.domain.usecases.user.DeleteUserUseCase
import br.com.joaovq.domain.usecases.user.GetUserByIdUseCase
import br.com.joaovq.plugins.AUTH_NAME
import com.market.core.security.CLAIM_NAME_ADMIN
import com.market.core.utils.exception.AuthorizationExceptionGroup
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import java.util.UUID

@Resource("user")
class UserResource {
    @Resource("{id}")
    class Id(val parent: UserResource = UserResource(), val id: String)
}

fun Route.initializeUserRouter(
    getUserByIdUseCase: GetUserByIdUseCase,
    deleteUserUseCase: DeleteUserUseCase
) {
    get<UserResource.Id> { userResource ->
        val id = UUID.fromString(userResource.id)
        call.respond(
            HttpStatusCode.OK,
            getUserByIdUseCase.invoke(id).toUser()
        )
    }
    authenticate(AUTH_NAME) {
        delete<UserResource.Id> { userResource ->
            val jwtPrincipal = call.principal<JWTPrincipal>()
            jwtPrincipal?.let { safePrincipal ->
                val isAdmin = safePrincipal.payload.getClaim(CLAIM_NAME_ADMIN)
                if (!isAdmin.asBoolean()) throw AuthorizationExceptionGroup.AuthorizationException()
            } ?: throw AuthorizationExceptionGroup.AuthorizationException()
            val id = UUID.fromString(userResource.id)
            deleteUserUseCase.invoke(id)
            call.respond(
                HttpStatusCode.NoContent
            )
        }
    }
}