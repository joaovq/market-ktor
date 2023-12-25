package com.example.di

import com.example.core.security.BCryptPasswordHasher
import com.example.core.security.PasswordHash
import com.example.dao.employer.EmployerDao
import com.example.dao.employer.EmployerRepository
import com.example.dao.user.UserDao
import com.example.dao.user.UserRepository
import org.koin.core.scope.get
import org.koin.dsl.module

val daoModule = module {
    single<UserRepository> { UserDao(get()) }
    single<EmployerRepository> { EmployerDao(get()) }
    single <PasswordHash> { BCryptPasswordHasher() }
}