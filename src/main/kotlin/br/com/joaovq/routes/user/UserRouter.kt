package br.com.joaovq.routes.user

import br.com.joaovq.data.models.UserRole
import br.com.joaovq.domain.mapper.toUser
import br.com.joaovq.domain.usecases.user.DeleteUserUseCase
import br.com.joaovq.domain.usecases.user.GetUserByIdUseCase
import br.com.joaovq.plugins.AUTH_NAME
import br.com.joaovq.plugins.custom.withRole
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

@Resource("user")
data class UserResource(val query: String = "") {
    @Resource("{id}")
    class Id(val parent: UserResource = UserResource(), val id: String)
    @Resource("{slug}")
    class Slug(val parent: UserResource = UserResource(), val slug: String)
}

fun Route.initializeUserRouter(
    getUserByIdUseCase: GetUserByIdUseCase,
    deleteUserUseCase: DeleteUserUseCase
) {
    withRole(AUTH_NAME, UserRole.USER) {
        get<UserResource.Id> { userResource ->
            val id = UUID.fromString(userResource.id)
            call.respond(
                HttpStatusCode.OK,
                getUserByIdUseCase.invoke(id).toUser()
            )
        }
    }
    withRole(AUTH_NAME, UserRole.ADMIN) {
        delete<UserResource.Id> { userResource ->
            val id = UUID.fromString(userResource.id)
            deleteUserUseCase.invoke(id)
            call.respond(
                HttpStatusCode.NoContent
            )
        }
    }
}