package com.example.core.utils.extension

import com.example.core.utils.AppEnviroment

fun String?.toEnvironment() = when(this) {
    "dev" -> AppEnviroment.DEV
    "prod" -> AppEnviroment.PROD
    else -> AppEnviroment.DEV
}