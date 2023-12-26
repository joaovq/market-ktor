package com.market.core.utils.extension

import com.market.core.utils.AppEnvironment


fun String?.toEnvironment() = when(this) {
    "dev" -> AppEnvironment.DEV
    "prod" -> AppEnvironment.PROD
    else -> AppEnvironment.DEV
}