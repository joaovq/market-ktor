package br.com.joaovq.plugins

import br.com.joaovq.domain.usecases.auth.GetProfileDataUseCase
import br.com.joaovq.domain.usecases.auth.SignInUseCase
import br.com.joaovq.domain.usecases.auth.SignUpUserUseCase
import br.com.joaovq.routes.auth.initializeAuthRouter
import br.com.joaovq.routes.employer.initializeEmployerRouter
import br.com.joaovq.routes.files.initializeFilesRouter
import br.com.joaovq.routes.user.initializeUserRouter
import com.market.core.utils.AppVersion
import com.market.core.utils.PropertiesConfigName
import com.market.core.utils.extension.toEnvironment
import com.market.core.utils.toActualEnvironment
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.getKoin
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
        get("/env") {
            call.respondText(
                env.toActualEnvironment()
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
        val signUpUseCase by inject<SignUpUserUseCase>()
        val getProfileDataUseCase by inject<GetProfileDataUseCase>()
        val koin = getKoin()
        route(AppVersion.V1_PATH) {
            initializeEmployerRouter()
            initializeAuthRouter(signInUseCase, signUpUseCase, getProfileDataUseCase)
            initializeFilesRouter()
            initializeUserRouter(koin.get(), koin.get())
        }
        singlePageApplication {
//            applicationRoute = "/"
//            filesPath = "react-app"
//            useResources = true
//            defaultPage = "index.html"
            applicationRoute = "/"
            react("react-app/dist")
        }
    }
}