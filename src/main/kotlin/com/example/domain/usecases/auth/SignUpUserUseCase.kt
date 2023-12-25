package com.example.domain.usecases.auth

import com.example.dao.user.UserRepository
import com.example.domain.request.CreateUserRequest
import com.example.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SignUpUserUseCase {
    suspend operator fun invoke(user: CreateUserRequest): User?
}

class SignUpUser(
    private val userRepository: UserRepository
) : SignUpUserUseCase {
    override suspend fun invoke(user: CreateUserRequest): User? = withContext(Dispatchers.IO) {
        userRepository.addNewUser(user)
    }
}