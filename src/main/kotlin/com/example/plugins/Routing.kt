package com.example.plugins

import com.example.core.utils.AppEnvironment
import com.example.core.utils.AppVersion
import com.example.core.utils.PropertiesConfigName
import com.example.core.utils.extension.toEnvironment
import com.example.domain.usecases.auth.GetProfileDataUseCase
import com.example.domain.usecases.auth.SignInUseCase
import com.example.domain.usecases.auth.SignUpUserUseCase
import com.example.routes.employer.initializeEmployerRouter
import com.example.routes.auth.initializeAuthRouter
import com.example.routes.files.initializeFilesRouter
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject
import java.io.File

fun Application.configureRouting() {
    val env = environment.config.propertyOrNull(PropertiesConfigName.KTOR_ENVIRON)?.getString().toEnvironment()
    install(Resources)
//    ShutDown Application in code
    /*    install(ShutDownUrl.ApplicationCallPlugin) {
            shutDownUrl = "/shutdown"
            exitCodeSupplier = { 0 }
        }*/
    routing {
        get("/") {
            call.respondText(
                when (env) {
                    AppEnvironment.DEV -> "Development"
                    AppEnvironment.PROD -> "Production"
                }
            )
        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", basePackage = "static") {
//            Base package: resources
            this@staticResources.enableAutoHeadResponse()
        }
        staticFiles("/files", dir = File("src/main/resources/static")) {
//            Base package: workdir
            this@staticFiles.enableAutoHeadResponse()
        }
//        TODO Pass for function separated
        val signInUseCase by inject<SignInUseCase>()
        val signUpuseCase by inject<SignUpUserUseCase>()
        val getProfileDataUseCase by inject<GetProfileDataUseCase>()
        route(AppVersion.V1_PATH) {
            initializeEmployerRouter()
            initializeAuthRouter(signInUseCase, signUpuseCase, getProfileDataUseCase)
            initializeFilesRouter()
        }
        singlePageApplication {
         //   TODO Create React app
            react("react-app")
        }
    }
}