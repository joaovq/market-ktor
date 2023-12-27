package br.com.joaovq.dao.exchange

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.util.*
import java.io.FileReader
import java.util.*

class ExchangeService(
    private val client: HttpClient
) {

    private var api: HttpClient = client.config {
        url {
            protocol = URLProtocol.HTTPS
            host = "brapi.dev" //prop["BRAPI_HOST"].toString()
            path("/api")
        }
    }

    init {
/*        val prop = Properties()
        val reader = FileReader("api.properties")
        prop.load(reader)*/
    }

    suspend fun get() {
        client.get() {

        }
    }
}