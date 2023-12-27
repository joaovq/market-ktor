package br.com.joaovq.plugins.custom

import br.com.joaovq.data.models.UserRole
import br.com.joaovq.data.models.toUserRole
import com.market.core.security.CLAIM_NAME_ADMIN
import com.market.core.utils.exception.AuthorizationExceptionGroup
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*

class AuthConfig {
    val roles: Set<UserRole> = UserRole.entries.toSet()

    // lateinit var getRoles: (user: Principal) -> Set<UserRole>
    lateinit var userRole: UserRole
}

val RoleAuthorization = createRouteScopedPlugin(
    "RoleAuthorizationPlugin",
    createConfiguration = ::AuthConfig
) {
    on(AuthenticationChecked) { call ->
        val jwtPrincipal = call.principal<JWTPrincipal>()
        jwtPrincipal?.let { safePrincipal ->
            val claim = safePrincipal.payload.getClaim(CLAIM_NAME_ADMIN)
            val mappedRole = claim.asString().toUserRole()
            if (mappedRole == UserRole.ADMIN) return@let
            else if (pluginConfig.userRole != mappedRole || !pluginConfig.roles.contains(mappedRole)) {
                throw AuthorizationExceptionGroup.NotAuthorizedException()
            }
        } ?: throw AuthorizationExceptionGroup.AuthorizationException()
    }
}

fun Route.withRole(name: String? = null, role: UserRole, build: Route.() -> Unit) {
    authenticate(name) {
        install(RoleAuthorization) {
            userRole = role
        }
        build()
    }
}