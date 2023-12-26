package com.market.core.utils.handler

import kotlinx.serialization.Serializable

@Serializable
data class ValidationMessageResponse(
    val message: String,
    val field: String? = null
)
