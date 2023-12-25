package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

@Serializable
data class Employer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val address: String,
    val salary: Double,
    val user: User
)

class EmployerEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<EmployerEntity>(Employers)

    var firstName by Employers.firstName
    var lastName by Employers.lastName
    var cpf by Employers.cpf
    var address by Employers.address
    var salary by Employers.salary
    var user by UserEntity referencedOn Employers.user
}

data object Employers : UUIDTable("employer_tb") {
    val firstName = varchar("employer_first_name", 1024)
    val lastName = varchar("employer_last_name", 1024)
    val cpf = varchar("employer_cpf", 1024)
    val address = varchar("employer_address", 1024)
    val salary = decimal("employer_salary", precision = 30, 3)
    val user = reference("user_id", Users.id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
}