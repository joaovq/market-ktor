package com.example.schema.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String
)
