package com.example.plugins

import com.example.di.daoModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    koin {
        slf4jLogger()
        modules(
            daoModule
        )
    }
}