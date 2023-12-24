package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCORS() {
    val origins = environment.config.propertyOrNull("cors.origins")?.toString()?.split(",")
    install(CORS) {
        origins?.onEach { origin ->
            allowHost(origin)
        }
        if (origins.isNullOrEmpty()) anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
    }
}