package com.example.core.security

import at.favre.lib.crypto.bcrypt.BCrypt

class BCryptPasswordHasher: PasswordHash {
    override fun encryptPassword(value: String): String {
        return BCrypt.withDefaults().hashToString(12, value.toCharArray())
    }

    override fun check(value: String, bcryptHashString: String): BCrypt.Result {
        return BCrypt.verifyer().verify(value.toCharArray(), bcryptHashString)
    }
}