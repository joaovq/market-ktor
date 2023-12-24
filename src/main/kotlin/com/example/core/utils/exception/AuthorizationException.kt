package com.example.core.utils.exception

class AuthorizationException: RuntimeException("Token is not valid or has expired") {
}
