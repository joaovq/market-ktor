package br.com.joaovq.data.models


//TODO CREATE USER ROLE TABLE
enum class UserRole(val value: String) {
    ADMIN("admin"),
    USER("user")
}

fun String?.toUserRole() =
    when(this) {
        "admin" -> UserRole.ADMIN
        "user" -> UserRole.USER
        else -> null
    }