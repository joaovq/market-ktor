package com.example.di

import com.example.domain.usecases.auth.*
import org.koin.dsl.module

val domainModule = module {
    single<SignInUseCase> { SignInUser(get(), get()) }
    single<SignUpUserUseCase> { SignUpUser(get()) }
    single<GetProfileDataUseCase> { GetProfileData(get()) }
}