package br.com.joaovq

import br.com.joaovq.data.DatabaseFactory
import br.com.joaovq.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import java.io.FileReader
import java.util.*


fun main(args: Array<String>) = EngineMain.main(args = args) /*{
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

@Suppress("unused")
fun Application.module() {
    DatabaseFactory.init(environment.config)
    configureKoin()
    configureMonitoring()
    configureSerialization()
    configureMetrics()
    configureCORS()
    configureHTTP()
    configureSecurity()
    configureStatusPage()
    configureRequestValidation()
    configureRouting()
}
