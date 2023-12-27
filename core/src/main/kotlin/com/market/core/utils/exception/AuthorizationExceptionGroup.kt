package com.market.core.utils.exception

sealed class AuthorizationExceptionGroup(
    override val message: String
): RuntimeException(message) {
    class AuthorizationException: AuthorizationExceptionGroup("Token is not valid or has expired")
    class NotAuthorizedException: AuthorizationExceptionGroup("Access not permitted")
    class InvalidCredentialsException: AuthorizationExceptionGroup("Invalid username or password")
}
