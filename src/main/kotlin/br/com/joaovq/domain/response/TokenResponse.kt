package br.com.joaovq.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String
)
