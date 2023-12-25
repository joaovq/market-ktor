package com.example.dao.employer

import com.example.core.utils.exception.BusinessExceptionGroup
import com.example.dao.DatabaseFactory.dbQuery
import com.example.dao.user.UserRepository
import com.example.models.Employer
import com.example.models.EmployerEntity
import com.example.domain.mapper.toEmployer
import com.example.domain.request.CreateEmployerRequest
import java.math.BigDecimal
import java.util.*

class EmployerDao(
    val userRepository: UserRepository
) : EmployerRepository {
    override suspend fun findAllEmployers(): List<Employer> = dbQuery {
        EmployerEntity.all().map(EmployerEntity::toEmployer)
    }

    override suspend fun addNewEmployer(employer: CreateEmployerRequest): Employer {
        val userEntity = userRepository.findUserById(UUID.fromString(employer.userId))
            ?: throw BusinessExceptionGroup.UserNotFoundException()
        return dbQuery {
            val newEmployer = EmployerEntity.new {
                firstName = employer.firstName
                lastName = employer.lastName
                cpf = employer.cpf
                salary = BigDecimal.valueOf(employer.salary)
                address = employer.address
                user = userEntity
            }
            newEmployer.toEmployer()
        }
    }
}