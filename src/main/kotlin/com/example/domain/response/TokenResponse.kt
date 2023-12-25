package com.example.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String
)
