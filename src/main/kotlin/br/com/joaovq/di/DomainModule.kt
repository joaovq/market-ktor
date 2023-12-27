package br.com.joaovq.di

import br.com.joaovq.domain.usecases.auth.*
import br.com.joaovq.domain.usecases.user.DeleteUser
import br.com.joaovq.domain.usecases.user.DeleteUserUseCase
import br.com.joaovq.domain.usecases.user.GetUserById
import br.com.joaovq.domain.usecases.user.GetUserByIdUseCase
import org.koin.dsl.module

val domainModule = module {
    single<SignInUseCase> { SignInUser(get(), get(), get()) }
    single<SignUpUserUseCase> { SignUpUser(get(), get()) }
    single<GetProfileDataUseCase> { GetProfileData(get(), get()) }
    single<GetUserByIdUseCase> { GetUserById(get(), get()) }
    single<DeleteUserUseCase> { DeleteUser(get(), get()) }
}