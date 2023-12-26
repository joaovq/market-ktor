package com.market.core.utils.models

object UserTableValues: TableValues {
    override val tableName: String = "user_tb"
    override val prefix: String = "user"

    const val USER_USERNAME = "user_username"
    const val USER_PASSWORD_NAME = "user_password"
    const val USER_EMAIL_NAME = "user_email"
    const val USER_IS_ACTIVE_NAME = "user_active"
    const val USER_ROLE_NAME = "user_role"
    const val USER_CREATED_AT_NAME = "user_created"
}