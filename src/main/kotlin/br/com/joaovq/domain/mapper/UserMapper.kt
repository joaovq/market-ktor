package br.com.joaovq.domain.mapper

import br.com.joaovq.data.models.User
import br.com.joaovq.data.models.UserEntity


fun UserEntity.toUser() = User(
    id = this.id.toString(),
    username = this.username,
    email = this.email,
    isActive = this.isActive,
    role = this.role,
    createdAt = this.createdAt.toString()
)