package br.com.joaovq.domain.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateEmployerRequest(
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val address: String,
    val salary: Double,
    val userId: String
)