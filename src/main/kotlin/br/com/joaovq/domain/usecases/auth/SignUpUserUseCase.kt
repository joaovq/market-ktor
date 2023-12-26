package br.com.joaovq.domain.usecases.auth

import br.com.joaovq.dao.user.UserRepository
import br.com.joaovq.data.models.User
import br.com.joaovq.domain.request.CreateUserRequest
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