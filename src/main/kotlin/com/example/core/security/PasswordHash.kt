package com.example.core.security

import at.favre.lib.crypto.bcrypt.BCrypt

interface PasswordHash {
    fun encryptPassword(value: String): String
    fun check(value: String, bcryptHashString: String): BCrypt.Result
}