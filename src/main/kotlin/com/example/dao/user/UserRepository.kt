package com.example.dao.user

import com.example.models.User
import com.example.models.UserEntity
import com.example.domain.request.CreateUserRequest
import java.util.UUID

interface UserRepository {
    suspend fun findAll():List<User>
    suspend fun findUserByUsername(username: String): UserEntity?
    suspend fun findUserById(id: UUID): UserEntity?
    suspend fun addNewUser(user: CreateUserRequest): User?
}