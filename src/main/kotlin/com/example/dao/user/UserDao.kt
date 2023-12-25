package com.example.dao.user

import com.example.core.security.BCryptPasswordHasher
import com.example.core.security.PasswordHash
import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.User
import com.example.models.UserEntity
import com.example.models.Users
import com.example.schema.mapper.toUser
import com.example.schema.request.CreateUserRequest
import org.jetbrains.exposed.sql.*
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