package br.com.joaovq.data.models

import br.com.joaovq.data.models.Users.default
import com.market.core.utils.models.UserTableValues
import io.ktor.server.auth.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

@Serializable
data class User(
    val id: String = "",
    val username: String,
    val email: String,
    val isActive: Boolean,
    val role: UserRole,
    val createdAt: String
)

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id), Principal {
    companion object : UUIDEntityClass<UserEntity>(Users)

    var username by Users.username
    var password by Users.password
    var email by Users.email
    var isActive by Users.isActive
    var role by Users.role
    val createdAt by Users.createdAt
}


object Users : UUIDTable(name = UserTableValues.tableName) {
    val username = varchar(UserTableValues.USER_USERNAME, 128).uniqueIndex()
    val password = varchar(UserTableValues.USER_PASSWORD_NAME, 1024)
    val email = varchar(UserTableValues.USER_EMAIL_NAME, 1024).uniqueIndex()
    val isActive = bool(UserTableValues.USER_IS_ACTIVE_NAME).default(true)
    val role = enumerationByName(UserTableValues.USER_ROLE_NAME, 30, UserRole::class).default(UserRole.USER)
    val createdAt = datetime(UserTableValues.USER_CREATED_AT_NAME).nullable().clientDefault { LocalDateTime.now() }
    //TODO CREATE TABLE ROLE AND PUT RELATION MANY TO MANY
}