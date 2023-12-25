package com.example.schema.mapper

import com.example.models.User
import com.example.models.UserEntity
import com.example.models.UserRole
import com.example.schema.request.CreateUserRequest


fun UserEntity.toUser() = User(
    id = this.id.toString(),
    username = this.username,
    email = this.email,
    isActive = this.isActive,
    role = this.role,
)