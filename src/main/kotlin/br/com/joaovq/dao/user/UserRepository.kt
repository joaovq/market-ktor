package br.com.joaovq.dao.user

import br.com.joaovq.data.models.User
import br.com.joaovq.data.models.UserEntity
import br.com.joaovq.domain.request.CreateUserRequest
import java.util.*

interface UserRepository {
    suspend fun findAll(): List<User>
    suspend fun findUserByUsername(username: String): UserEntity?
    suspend fun findUserById(id: UUID): UserEntity
    suspend fun addNewUser(user: CreateUserRequest): User?
    suspend fun deleteUser(id: UUID)
}