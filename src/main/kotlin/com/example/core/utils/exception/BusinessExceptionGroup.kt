package com.example.core.utils.exception

sealed class BusinessExceptionGroup(message: String): RuntimeException(message) {
    class UserNotFoundException: BusinessExceptionGroup("User not found!")
}