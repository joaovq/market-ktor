package com.market.core.utils

enum class AppEnvironment(
    val type: String
) {
    DEV("dev"),
    PROD("dev");
}

fun AppEnvironment.toActualEnvironment() = when (this) {
    AppEnvironment.DEV -> "Development"
    AppEnvironment.PROD -> "Production"
}