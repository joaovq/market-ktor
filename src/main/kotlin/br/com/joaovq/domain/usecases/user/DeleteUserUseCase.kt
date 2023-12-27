package br.com.joaovq.domain.usecases.user

import br.com.joaovq.dao.user.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

interface DeleteUserUseCase {
    suspend operator fun invoke(id: UUID)
}

class DeleteUser(
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): DeleteUserUseCase {
    override suspend fun invoke(id: UUID) = withContext(coroutineDispatcher) {
        userRepository.deleteUser(id)
    }
}