package com.example.models

import com.example.models.Users.default
import com.example.models.Users.uniqueIndex
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

@Serializable
data class User(
    val id: String = "",
    val username: String,
    val email: String,
    val isActive: Boolean,
    val role: UserRole
)

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(Users)
    var userId by Users.id
    var username by Users.username
    var password by Users.password
    var email by Users.email
    var isActive by Users.isActive
    var role by Users.role
}

object Users : UUIDTable(name = "user_tb") {
    val username = varchar("user_username", 128).uniqueIndex()
    val password = varchar("user_password", 1024)
    val email = varchar("user_email", 1024)
    val isActive = bool("user_is_active").default(true)
    val role = enumeration("user_role", UserRole::class).default(UserRole.USER)
}