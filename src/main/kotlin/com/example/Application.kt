package com.example

import com.example.dao.DatabaseFactory
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args =args) /*{
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
        watchPaths = listOf("classes"),
        configure = {

        }
    )
        .start(wait = true)
}*/

fun Application.module() {
    DatabaseFactory.init()
    configureMonitoring()
    configureSerialization()
    configureCORS()
    configureHTTP()
    configureSecurity()
    configureRouting()
    configureStatusPage()
    configureRequestValidation()
}
