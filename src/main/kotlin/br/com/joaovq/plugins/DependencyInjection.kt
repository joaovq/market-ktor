package br.com.joaovq.plugins

import br.com.joaovq.di.ClientModule
import br.com.joaovq.di.DispatcherModule
import br.com.joaovq.di.daoModule
import br.com.joaovq.di.domainModule
import io.ktor.server.application.*
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    koin {
        slf4jLogger()
        modules(
            daoModule,
            domainModule,
            ClientModule().module,
            DispatcherModule().module
        )
    }
}