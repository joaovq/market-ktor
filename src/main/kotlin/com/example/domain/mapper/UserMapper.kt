package com.example.domain.mapper

import com.example.models.User
import com.example.models.UserEntity


fun UserEntity.toUser() = User(
    id = this.id.toString(),
    username = this.username,
    email = this.email,
    isActive = this.isActive,
    role = this.role,
)