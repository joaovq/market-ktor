package br.com.joaovq.plugins

import br.com.joaovq.domain.request.CreateUserRequest
import br.com.joaovq.domain.request.LoginRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<String> { bodyText ->
            if (!bodyText.startsWith("Hello"))
                ValidationResult.Invalid("Body text should start with 'Hello'")
            else ValidationResult.Valid
        }
        validate<LoginRequest> { user ->
            val reasons = mutableListOf<String>()
            if (user.username.isBlank())
                reasons.add("Username cannot be blank")
            if (user.password.isBlank())
                reasons.add("Password cannot be blank")

            if (reasons.isNotEmpty()) ValidationResult.Invalid(reasons) else ValidationResult.Valid
        }
        validate<CreateUserRequest> { user ->
            val reasons = mutableListOf<String>()
            if (user.username.isBlank())
                reasons.add("Username cannot be blank")
            if (user.password.isBlank())
                reasons.add("Password cannot be blank")
            if (user.email.isBlank())
                reasons.add("Email cannot be blank")

            if (reasons.isNotEmpty()) ValidationResult.Invalid(reasons) else ValidationResult.Valid
        }
    }
}