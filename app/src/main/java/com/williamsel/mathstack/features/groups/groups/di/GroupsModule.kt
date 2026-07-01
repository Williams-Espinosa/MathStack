package com.williamsel.mathstack.features.groups.di

import com.williamsel.mathstack.features.groups.data.datasource.api.GroupsApi
import com.williamsel.mathstack.features.groups.data.repositories.GroupsRepositoryImpl
import com.williamsel.mathstack.features.groups.domain.repositories.GroupsRepository
import com.williamsel.mathstack.features.groups.domain.usecases.GetGroupsUseCase
import com.williamsel.mathstack.features.groups.domain.usecases.GroupsUseCases
import com.williamsel.mathstack.features.groups.domain.usecases.LeaveGroupUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GroupsModule {

    @Binds
    @Singleton
    abstract fun bindGroupsRepository(
        impl: GroupsRepositoryImpl
    ): GroupsRepository

    companion object {

        @Provides
        @Singleton
        fun provideGroupsApi(retrofit: Retrofit): GroupsApi =
            retrofit.create(GroupsApi::class.java)

        @Provides
        @Singleton
        fun provideGroupsUseCases(
            getGroups: GetGroupsUseCase,
            leaveGroup: LeaveGroupUseCase
        ): GroupsUseCases = GroupsUseCases(
            getGroups  = getGroups,
            leaveGroup = leaveGroup
        )
    }
}