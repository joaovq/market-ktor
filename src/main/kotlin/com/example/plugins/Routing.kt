package com.example.plugins

import com.example.core.utils.AppEnviroment
import com.example.core.utils.AppVersion
import com.example.core.utils.PropertiesConfigName
import com.example.core.utils.extension.toEnvironment
import com.example.routes.article.initRouterArticle
import com.example.routes.auth.initializeAuthRouter
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import java.io.File

fun Application.configureRouting() {
    val env = environment.config.propertyOrNull(PropertiesConfigName.KTOR_ENVIRON)?.getString().toEnvironment()
    install(Resources)
    routing {
        get {
            call.respondText(when (env) {
                AppEnviroment.DEV -> "Development"
                AppEnviroment.PROD -> "Production"
            })
        }
        // Static plugin. Try to access `/static/index.html`
        staticFiles("/static", dir = File("")) {
            staticResources("static", basePackage = null)
        }
        route(AppVersion.V1_PATH) {
            initRouterArticle()
            initializeAuthRouter()
        }
    }
}