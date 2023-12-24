package com.example.schema.mapper

import com.example.models.User
import com.example.schema.request.CreateUserRequest

fun CreateUserRequest.toEntity() = User(
    username = this.username,
    password = this.password,
    email = this.email,
    isActive = true
)