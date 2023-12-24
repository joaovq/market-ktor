package com.example.plugins

import com.example.core.utils.exception.AuthorizationException
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
            if (cause is AuthorizationException) {
                call.respond(
                    message = ErrorResponse(
                        cause.message.toString(),
                        HttpStatusCode.Unauthorized.value
                    ), status = HttpStatusCode.Unauthorized
                )
            } else {
                call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
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