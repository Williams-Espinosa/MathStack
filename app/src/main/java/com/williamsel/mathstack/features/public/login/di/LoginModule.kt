package com.williamsel.mathstack.features.public.login.di

import com.williamsel.mathstack.features.public.login.data.datasource.api.LoginApi
import com.williamsel.mathstack.features.public.login.data.repositories.LoginRepositoryImpl
import com.williamsel.mathstack.features.public.login.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    companion object {
        @Provides
        @Singleton
        fun provideLoginApi(retrofit: Retrofit): LoginApi {
            return retrofit.create(LoginApi::class.java)
        }
    }
}