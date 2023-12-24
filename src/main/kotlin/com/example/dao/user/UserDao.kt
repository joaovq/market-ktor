package com.example.dao.user

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.User
import com.example.models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class UserDao : UserRepository {
    private fun resultRowToArticle(row: ResultRow) = User(
        id = row[Users.id].toString(),
        username = row[Users.username],
        password = row[Users.password],
        email = row[Users.email],
        isActive = row[Users.isActive],
    )

    override suspend fun findAll(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToArticle)
    }

    override suspend fun addNewUser(user: User): User? = dbQuery {
        val insertStatement = Users.insert { table ->
            table[username] = user.username
            table[password] = user.password
            table[email] = user.email
            table[isActive] = user.isActive
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToArticle)
    }
}

val userDao = UserDao()