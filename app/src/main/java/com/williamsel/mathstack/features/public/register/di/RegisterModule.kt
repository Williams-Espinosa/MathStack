package com.williamsel.mathstack.features.public.register.di

import com.williamsel.mathstack.features.public.register.data.datasource.api.RegisterApi
import com.williamsel.mathstack.features.public.register.data.repositories.RegisterRepositoryImpl
import com.williamsel.mathstack.features.public.register.domain.repositories.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterModule {

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository

    companion object {

        @Provides
        @Singleton
        fun provideRegisterApi(retrofit: Retrofit): RegisterApi {
            return retrofit.create(RegisterApi::class.java)
        }
    }
}