package br.com.joaovq.domain.usecases.user

import br.com.joaovq.dao.user.UserRepository
import br.com.joaovq.data.models.UserEntity
import com.market.core.utils.exception.BusinessExceptionGroup
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

interface GetUserByIdUseCase {
    suspend operator fun invoke(id: UUID): UserEntity
}

class GetUserById(
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetUserByIdUseCase {
    override suspend fun invoke(id: UUID): UserEntity = withContext(coroutineDispatcher) {
        userRepository.findUserById(id)
    }
}