package com.market.core.utils.models

object EmployerTableValues: TableValues {
    override val tableName: String = "employer_tb"
    override val prefix: String = "employer"

    const val EMPLOYER_FIRST_NAME = "employer_first_name"
    const val EMPLOYER_LAST_NAME = "employer_last_name"
    const val EMPLOYER_CPF_NAME = "employer_cpf"
    const val EMPLOYER_ADDRESS_NAME = "employer_address"
    const val EMPLOYER_SALARY_NAME = "employer_salary"
    const val EMPLOYER_USER_NAME = "user_id"
}