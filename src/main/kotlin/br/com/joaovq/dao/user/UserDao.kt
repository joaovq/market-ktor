package br.com.joaovq.dao.user

import br.com.joaovq.data.DatabaseFactory.dbQuery
import br.com.joaovq.data.models.User
import br.com.joaovq.data.models.UserEntity
import br.com.joaovq.data.models.Users
import br.com.joaovq.domain.mapper.toUser
import br.com.joaovq.domain.request.CreateUserRequest
import com.market.core.security.BCryptPasswordHasher
import com.market.core.security.PasswordHash
import java.util.*

class UserDao(
    private val passwordHasher: PasswordHash = BCryptPasswordHasher()
) : UserRepository {

    override suspend fun findAll(): List<User> = dbQuery {
        UserEntity.all().map(UserEntity::toUser)
    }

    override suspend fun findUserByUsername(username: String): UserEntity? = dbQuery {
        UserEntity.find { Users.username eq username }.singleOrNull()
    }

    override suspend fun findUserById(id: UUID): UserEntity? = dbQuery {
        UserEntity.findById(id)
    }


    override suspend fun addNewUser(user: CreateUserRequest): User = dbQuery {
        val newUser = UserEntity.new {
            this.username = user.username
            this.password = passwordHasher.encryptPassword(user.password)
            this.email = user.email
        }
        newUser.toUser()
    }
}