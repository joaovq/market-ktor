package br.com.joaovq.dao.crypto

import io.ktor.client.*
import io.ktor.client.request.*

class CryptoService(
    private val client: HttpClient
) {
    suspend fun get(){
        client.get("")
    }
}