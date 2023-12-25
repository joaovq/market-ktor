package com.example.dao.employer

import com.example.models.Employer
import com.example.domain.request.CreateEmployerRequest

interface EmployerRepository {
    suspend fun findAllEmployers(): List<Employer>
    suspend fun addNewEmployer(employer: CreateEmployerRequest): Employer
}
