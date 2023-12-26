package br.com.joaovq.plugins

import com.market.core.utils.exception.AuthorizationExceptionGroup
import com.market.core.utils.exception.BusinessExceptionGroup
import com.market.core.utils.handler.ErrorResponse
import com.market.core.utils.handler.ValidationMessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException

fun Application.configureStatusPage() {
    install(StatusPages) {
//        TODO separate in other packages and files
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }

        exception<AuthorizationExceptionGroup> { call, cause ->
            when (cause) {
                is AuthorizationExceptionGroup.AuthorizationException -> {
                    call.respond(
                        message = ErrorResponse(
                            cause.message,
                            HttpStatusCode.Unauthorized.value
                        ), status = HttpStatusCode.Unauthorized
                    )
                }

                is AuthorizationExceptionGroup.InvalidCredentialsException -> {
                    call.respond(
                        message = ErrorResponse(
                            cause.message,
                            HttpStatusCode.BadRequest.value
                        ), status = HttpStatusCode.BadRequest
                    )
                }
            }
        }


        exception<BusinessExceptionGroup> { call, cause ->
            when (cause) {
                is BusinessExceptionGroup.UserNotFoundException -> {
                    call.respond(
                        message = ErrorResponse(
                            cause.message.toString(),
                            HttpStatusCode.BadRequest.value
                        ), status = HttpStatusCode.BadRequest
                    )
                }
            }
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Page Not Found", status = status)
        }
        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.map { reason ->
                ValidationMessageResponse(
                    reason
                )
            })
        }
//        Handler SQL Exception
        exception<ExposedSQLException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    cause.message.toString(),
                    HttpStatusCode.BadRequest.value
                )
            )
        }

//        TODO handler NotImplementedError
    }
}