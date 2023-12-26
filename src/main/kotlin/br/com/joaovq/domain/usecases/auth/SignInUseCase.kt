package br.com.joaovq.domain.usecases.auth

import br.com.joaovq.dao.user.UserRepository
import br.com.joaovq.data.models.UserEntity
import br.com.joaovq.domain.request.LoginRequest
import com.market.core.security.PasswordHash
import com.market.core.utils.exception.AuthorizationExceptionGroup
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