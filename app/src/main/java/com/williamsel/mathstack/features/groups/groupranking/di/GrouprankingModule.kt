package com.williamsel.mathstack.features.groupranking.di

import com.williamsel.mathstack.features.groupranking.data.datasource.api.GrouprankingApi
import com.williamsel.mathstack.features.groupranking.data.repositories.GrouprankingRepositoryImpl
import com.williamsel.mathstack.features.groupranking.domain.repositories.GrouprankingRepository
import com.williamsel.mathstack.features.groupranking.domain.usecases.GetGlobalRankingUseCase
import com.williamsel.mathstack.features.groupranking.domain.usecases.GetGroupRankingUseCase
import com.williamsel.mathstack.features.groupranking.domain.usecases.GrouprankingUseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GrouprankingModule {

    @Binds
    @Singleton
    abstract fun bindGrouprankingRepository(
        impl: GrouprankingRepositoryImpl
    ): GrouprankingRepository

    companion object {

        @Provides
        @Singleton
        fun provideGrouprankingApi(retrofit: Retrofit): GrouprankingApi =
            retrofit.create(GrouprankingApi::class.java)

        @Provides
        @Singleton
        fun provideGrouprankingUseCases(
            getGroupRanking: GetGroupRankingUseCase,
            getGlobalRanking: GetGlobalRankingUseCase
        ): GrouprankingUseCases = GrouprankingUseCases(
            getGroupRanking  = getGroupRanking,
            getGlobalRanking = getGlobalRanking
        )
    }
}
