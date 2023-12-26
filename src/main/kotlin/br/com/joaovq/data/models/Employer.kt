package br.com.joaovq.data.models

import com.market.core.utils.models.EmployerTableValues
import kotlinx.serialization.Serializable
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

data object Employers : UUIDTable(EmployerTableValues.tableName) {
    val firstName = varchar(EmployerTableValues.EMPLOYER_FIRST_NAME, 1024)
    val lastName = varchar(EmployerTableValues.EMPLOYER_LAST_NAME, 1024)
    val cpf = varchar(EmployerTableValues.EMPLOYER_CPF_NAME, 1024).uniqueIndex()
    val address = varchar(EmployerTableValues.EMPLOYER_ADDRESS_NAME, 1024)
    val salary = decimal(EmployerTableValues.EMPLOYER_SALARY_NAME, precision = 30, 3)
    val user = reference(
        EmployerTableValues.EMPLOYER_USER_NAME,
        Users.id,
        onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.CASCADE
    )
//    Relation one to one
        .uniqueIndex()
}