package com.example.domain.usecases.auth

import com.example.core.security.BCryptPasswordHasher
import com.example.core.security.PasswordHash
import com.example.core.security.TokenFactory
import com.example.core.utils.exception.AuthorizationExceptionGroup
import com.example.dao.user.UserDao
import com.example.dao.user.UserRepository
import com.example.domain.request.LoginRequest
import com.example.domain.response.TokenResponse
import com.example.models.UserEntity
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SignInUseCase {
    suspend operator fun invoke(user: LoginRequest): UserEntity
}

class SignInUser(
    private val userRepository: UserRepository,
    private val passwordHash: PasswordHash
) : SignInUseCase {
    override suspend fun invoke(user: LoginRequest): UserEntity = withContext(Dispatchers.IO) {
        val userFound = userRepository.findUserByUsername(user.username)
            ?: throw AuthorizationExceptionGroup.InvalidCredentialsException()
        val bcryptResult = passwordHash.check(user.password, userFound.password)
        if (!bcryptResult.verified) {
            throw AuthorizationExceptionGroup.InvalidCredentialsException()
        }
        userFound
    }
}