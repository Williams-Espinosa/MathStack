package com.williamsel.mathstack.features.challenges.weeklychallenges.di

import com.williamsel.mathstack.features.challenges.weeklychallenges.data.datasource.api.WeeklychallengesApi
import com.williamsel.mathstack.features.challenges.weeklychallenges.data.repositories.WeeklychallengesRepositoryImpl
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.repositories.WeeklychallengesRepository
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.usecases.GetWeeklyChallengesUseCase
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.usecases.JoinChallengeUseCase
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.usecases.SaveSessionProgressUseCase
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.usecases.SubmitAnswerUseCase
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.usecases.WeeklychallengesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeeklychallengesModule {

    @Provides
    @Singleton
    fun provideWeeklychallengesApi(retrofit: Retrofit): WeeklychallengesApi =
        retrofit.create(WeeklychallengesApi::class.java)

    @Provides
    @Singleton
    fun provideWeeklychallengesRepository(
        api: WeeklychallengesApi
    ): WeeklychallengesRepository = WeeklychallengesRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideWeeklychallengesUseCases(
        repository: WeeklychallengesRepository
    ): WeeklychallengesUseCases = WeeklychallengesUseCases(
        getChallenges  = GetWeeklyChallengesUseCase(repository),
        joinChallenge  = JoinChallengeUseCase(repository),
        submitAnswer   = SubmitAnswerUseCase(repository),
        saveProgress   = SaveSessionProgressUseCase(repository)
    )
}
