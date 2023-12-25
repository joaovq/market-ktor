package com.example.core.utils.extension

import com.example.core.utils.AppEnvironment

fun String?.toEnvironment() = when(this) {
    "dev" -> AppEnvironment.DEV
    "prod" -> AppEnvironment.PROD
    else -> AppEnvironment.DEV
}