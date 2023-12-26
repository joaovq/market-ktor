package br.com.joaovq.dao.employer

import br.com.joaovq.dao.user.UserRepository
import br.com.joaovq.data.DatabaseFactory.dbQuery
import br.com.joaovq.data.models.Employer
import br.com.joaovq.data.models.EmployerEntity
import br.com.joaovq.domain.mapper.toEmployer
import br.com.joaovq.domain.request.CreateEmployerRequest
import com.market.core.utils.exception.BusinessExceptionGroup
import java.math.BigDecimal
import java.util.*

class EmployerDao(
    private val userRepository: UserRepository
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