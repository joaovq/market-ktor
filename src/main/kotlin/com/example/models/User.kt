package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(val id: String = "", val username: String, val password: String, val email: String, val isActive: Boolean)

object Users : Table() {
    val id = uuid("id").autoGenerate()
    val username = varchar("user_username", 128).uniqueIndex()
    val password = varchar("user_password", 1024)
    val email = varchar("user_email", 1024)
    val isActive = bool("user_is_active").default(true)
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}