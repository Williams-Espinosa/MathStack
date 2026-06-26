package com.williamsel.mathstack.features.private.creategroups.di

import com.williamsel.mathstack.features.private.creategroups.data.datasource.api.CreategroupsApi
import com.williamsel.mathstack.features.private.creategroups.data.repositories.CreategroupsRepositoryImpl
import com.williamsel.mathstack.features.private.creategroups.domain.repositories.CreategroupsRepository
import com.williamsel.mathstack.features.private.creategroups.domain.usecases.CreateGroupUseCase
import com.williamsel.mathstack.features.private.creategroups.domain.usecases.CreategroupsUseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CreategroupsModule {

    @Binds
    @Singleton
    abstract fun bindCreategroupsRepository(
        impl: CreategroupsRepositoryImpl
    ): CreategroupsRepository

    companion object {

        @Provides
        @Singleton
        fun provideCreategroupsApi(retrofit: Retrofit): CreategroupsApi =
            retrofit.create(CreategroupsApi::class.java)

        @Provides
        @Singleton
        fun provideCreategroupsUseCases(
            createGroup: CreateGroupUseCase
        ): CreategroupsUseCases = CreategroupsUseCases(
            createGroup = createGroup
        )
    }
}
