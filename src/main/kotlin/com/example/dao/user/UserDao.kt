package com.example.dao.user

import com.example.core.security.BCryptPasswordHasher
import com.example.core.security.PasswordHash
import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.User
import com.example.models.Users
import org.jetbrains.exposed.sql.*

class UserDao(
    private val passwordHasher: PasswordHash = BCryptPasswordHasher()
) : UserRepository {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id].toString(),
        username = row[Users.username],
        password = row[Users.password],
        email = row[Users.email],
        isActive = row[Users.isActive],
    )

    override suspend fun findAll(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun findUserByUsername(username: String): User? = dbQuery {
        Users.select { Users.username eq username }
            .map(::resultRowToUser)
            .singleOrNull()
    }


    override suspend fun addNewUser(user: User): User? = dbQuery {
        val insertStatement = Users.insert { table ->
            table[username] = user.username
            table[password] = passwordHasher.encryptPassword(user.password)
            table[email] = user.email
            table[isActive] = user.isActive
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }
}

val userDao = UserDao()