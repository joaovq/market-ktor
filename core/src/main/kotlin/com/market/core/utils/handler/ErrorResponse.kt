package com.market.core.utils.handler

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val statusCode: Int
)