package com.williamsel.mathstack.features.settings.accountmanagement.di

import com.williamsel.mathstack.features.settings.accountmanagement.data.datasource.api.AccountmanagementApi
import com.williamsel.mathstack.features.settings.accountmanagement.data.repositories.AccountmanagementRepositoryImpl
import com.williamsel.mathstack.features.settings.accountmanagement.domain.repositories.AccountmanagementRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountmanagementModule {

    @Provides
    @Singleton
    fun provideAccountmanagementApi(retrofit: Retrofit): AccountmanagementApi =
        retrofit.create(AccountmanagementApi::class.java)

    @Provides
    @Singleton
    fun provideAccountmanagementRepository(
        api: AccountmanagementApi
    ): AccountmanagementRepository = AccountmanagementRepositoryImpl(api)
}
