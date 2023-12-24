package com.example.core.utils.exception

sealed class AuthorizationExceptionGroup(
    override val message: String
): RuntimeException(message) {
    class AuthorizationException: AuthorizationExceptionGroup("Token is not valid or has expired")
    class InvalidCredentialsException: AuthorizationExceptionGroup("Invalid username or password")
}
