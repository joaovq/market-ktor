package com.example.core.utils.handler

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val statusCode: Int
)