package br.com.joaovq.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class DispatcherModule {
    @Single
    fun dispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}