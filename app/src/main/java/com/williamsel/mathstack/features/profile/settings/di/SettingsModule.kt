package com.williamsel.mathstack.features.settings.di

import com.williamsel.mathstack.features.settings.data.datasource.api.SettingsApi
import com.williamsel.mathstack.features.settings.data.repositories.SettingsRepositoryImpl
import com.williamsel.mathstack.features.settings.domain.repositories.SettingsRepository
import com.williamsel.mathstack.features.settings.domain.usecases.GetSettingsUseCase
import com.williamsel.mathstack.features.settings.domain.usecases.LogoutUseCase
import com.williamsel.mathstack.features.settings.domain.usecases.SettingsUseCases
import com.williamsel.mathstack.features.settings.domain.usecases.UpdateSettingsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

    companion object {

        @Provides
        @Singleton
        fun provideSettingsApi(retrofit: Retrofit): SettingsApi =
            retrofit.create(SettingsApi::class.java)

        @Provides
        @Singleton
        fun provideSettingsUseCases(
            getSettings: GetSettingsUseCase,
            updateSettings: UpdateSettingsUseCase,
            logout: LogoutUseCase
        ): SettingsUseCases = SettingsUseCases(
            getSettings    = getSettings,
            updateSettings = updateSettings,
            logout         = logout
        )
    }
}