package com.example.dao.user

import com.example.models.User

interface UserRepository {
    suspend fun findAll():List<User>
    suspend fun addNewUser(user: User): User?
}