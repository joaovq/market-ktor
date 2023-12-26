package br.com.joaovq.dao.employer

import br.com.joaovq.data.models.Employer
import br.com.joaovq.domain.request.CreateEmployerRequest

interface EmployerRepository {
    suspend fun findAllEmployers(): List<Employer>
    suspend fun addNewEmployer(employer: CreateEmployerRequest): Employer
}
