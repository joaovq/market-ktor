package br.com.joaovq.domain.mapper

import br.com.joaovq.data.models.Employer
import br.com.joaovq.data.models.EmployerEntity

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