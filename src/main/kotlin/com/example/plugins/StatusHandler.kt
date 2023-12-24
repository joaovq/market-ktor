package com.example.plugins

import com.example.core.utils.exception.AuthorizationExceptionGroup
import com.example.core.utils.handler.ErrorResponse
import com.example.core.utils.handler.ValidationMessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {
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
    }
}