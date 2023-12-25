package com.example.models

import com.example.core.utils.models.UserTableValues
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

object Users : UUIDTable(name = UserTableValues.tableName) {
    val username = varchar(UserTableValues.USER_USERNAME, 128).uniqueIndex()
    val password = varchar(UserTableValues.USER_PASSWORD_NAME, 1024)
    val email = varchar(UserTableValues.USER_EMAIL_NAME, 1024)
    val isActive = bool(UserTableValues.USER_IS_ACTIVE_NAME).default(true)
    val role = enumeration(UserTableValues.USER_ROLE_NAME, UserRole::class).default(UserRole.USER)
}