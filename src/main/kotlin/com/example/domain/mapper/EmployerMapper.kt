package com.example.domain.mapper

import com.example.models.Employer
import com.example.models.EmployerEntity

fun EmployerEntity.toEmployer() =
    Employer(
        this.id.value.toString(),
        this.firstName,
        this.lastName,
        this.cpf,
        this.address,
        this.salary.toDouble(),
        this.user.toUser()
    )