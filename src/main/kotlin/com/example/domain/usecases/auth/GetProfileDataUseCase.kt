package com.example.domain.usecases.auth

import com.example.dao.user.UserRepository
import com.example.domain.mapper.toUser
import com.example.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetProfileDataUseCase {
    suspend operator fun invoke(username: String): User?
}

class GetProfileData(
    private val userRepository: UserRepository
): GetProfileDataUseCase {
    override suspend fun invoke(username: String): User? = withContext(Dispatchers.IO) {
        userRepository.findUserByUsername(username)?.toUser()
    }
}