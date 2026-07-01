package com.williamsel.mathstack.features.profile.profile.di

import com.williamsel.mathstack.features.profile.profile.data.datasource.api.ProfileApi
import com.williamsel.mathstack.features.profile.profile.data.repositories.ProfileRepositoryImpl
import com.williamsel.mathstack.features.profile.profile.domain.repositories.ProfileRepository
import com.williamsel.mathstack.features.profile.profile.domain.usecases.GetProfileUseCase
import com.williamsel.mathstack.features.profile.profile.domain.usecases.ProfileUseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    companion object {

        @Provides
        @Singleton
        fun provideProfileApi(retrofit: Retrofit): ProfileApi =
            retrofit.create(ProfileApi::class.java)

        @Provides
        @Singleton
        fun provideProfileUseCases(
            getProfile: GetProfileUseCase
        ): ProfileUseCases = ProfileUseCases(getProfile = getProfile)
    }
}