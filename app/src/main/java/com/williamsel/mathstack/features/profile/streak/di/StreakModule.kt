package com.williamsel.mathstack.features.profile.streak.di

import com.williamsel.mathstack.features.profile.streak.data.datasource.api.StreakApi
import com.williamsel.mathstack.features.profile.streak.data.repositories.StreakRepositoryImpl
import com.williamsel.mathstack.features.profile.streak.domain.repositories.StreakRepository
import com.williamsel.mathstack.features.profile.streak.domain.usecases.GetStreakUseCase
import com.williamsel.mathstack.features.profile.streak.domain.usecases.StreakUseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StreakModule {

    @Binds
    @Singleton
    abstract fun bindStreakRepository(
        impl: StreakRepositoryImpl
    ): StreakRepository

    companion object {

        @Provides
        @Singleton
        fun provideStreakApi(retrofit: Retrofit): StreakApi =
            retrofit.create(StreakApi::class.java)

        @Provides
        @Singleton
        fun provideStreakUseCases(
            getStreak: GetStreakUseCase
        ): StreakUseCases = StreakUseCases(getStreak = getStreak)
    }
}