package br.com.joaovq.di

import br.com.joaovq.dao.crypto.CryptoService
import br.com.joaovq.dao.employer.EmployerDao
import br.com.joaovq.dao.employer.EmployerRepository
import br.com.joaovq.dao.user.UserDao
import br.com.joaovq.dao.user.UserRepository
import com.market.core.security.BCryptPasswordHasher
import com.market.core.security.PasswordHash
import org.koin.dsl.module

val daoModule = module {
    single<UserRepository> { UserDao(get()) }
    single<EmployerRepository> { EmployerDao(get()) }
    single<PasswordHash> { BCryptPasswordHasher() }
    single<CryptoService> { CryptoService(get()) }
}