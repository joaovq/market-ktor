package br.com.joaovq.di

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.cache.storage.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import java.nio.file.Files
import java.nio.file.Paths

@Module
@ComponentScan
class ClientModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Single
    fun getClient(): HttpClient = HttpClient(CIO) {
        developmentMode = true
        install(HttpTimeout) {
            requestTimeoutMillis = 1000
            connectTimeoutMillis = 3000
            socketTimeoutMillis = 3000
        }
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    explicitNulls = true
                }
            )
        }
        install(HttpCache) {
            val cacheFile = Files.createDirectories(Paths.get("build/cache")).toFile()
            publicStorage(FileStorage(cacheFile))
        }
        install(UserAgent) { agent = "Market App" }
//        Retry http requests
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 5)
            exponentialDelay()

            // Retry conditions
            modifyRequest { request ->
                request.headers.append("x-retry-count", retryCount.toString())
            }

            maxRetries = 5
            retryIf { request, response ->
                !response.status.isSuccess()
            }
            retryOnExceptionIf { request, cause ->
                // cause is NetworkError
                cause is ClientRequestException
            }
            delayMillis { retry ->
                retry * 3000L
            }
        }

//        Validation
        HttpResponseValidator {
            validateResponse { response ->
                val error: ClientRequestException = response.body()
                if (error.response.status == HttpStatusCode.BadRequest) {
                    throw RuntimeException("Error in request")
                }
            }
            handleResponseExceptionWithRequest { exception, request ->
                val clientException = exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
                val exceptionResponse = clientException.response
                if (exceptionResponse.status == HttpStatusCode.NotFound) {
                    val exceptionResponseText = exceptionResponse.bodyAsText()
                    throw RuntimeException("Error in request")
                }
            }
        }
    }
}

/*OR*/

/*
val clientModule = module {
    single<HttpClient> {
        HttpClient() {
            this.developmentMode = true
            install(HttpTimeout) {
                requestTimeoutMillis = 1000
                connectTimeoutMillis = 3000
                socketTimeoutMillis = 3000
            }
        }
    }
}*/
