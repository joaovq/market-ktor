package br.com.joaovq.domain.usecases.auth

import br.com.joaovq.dao.user.UserRepository
import br.com.joaovq.data.models.User
import br.com.joaovq.domain.mapper.toUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetProfileDataUseCase {
    suspend operator fun invoke(username: String): User?
}

class GetProfileData(
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : GetProfileDataUseCase {
    override suspend fun invoke(username: String): User? = withContext(coroutineDispatcher) {
        userRepository.findUserByUsername(username)?.toUser()
    }
}