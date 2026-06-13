package com.williamsel.mathstack.features.public.codesent.di

import com.williamsel.mathstack.features.public.codesent.data.datasource.api.CodesentApi
import com.williamsel.mathstack.features.public.codesent.data.repositories.CodesentRepositoryImpl
import com.williamsel.mathstack.features.public.codesent.domain.repositories.CodesentRepository
import com.williamsel.mathstack.features.public.codesent.domain.usecases.CodesentUseCases
import com.williamsel.mathstack.features.public.codesent.domain.usecases.ResendCodeUseCase
import com.williamsel.mathstack.features.public.codesent.domain.usecases.VerifyCodeUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CodesentModule {

    @Binds
    @Singleton
    abstract fun bindCodesentRepository(
        impl: CodesentRepositoryImpl
    ): CodesentRepository

    companion object {

        @Provides
        @Singleton
        fun provideCodesentApi(retrofit: Retrofit): CodesentApi =
            retrofit.create(CodesentApi::class.java)

        @Provides
        @Singleton
        fun provideCodesentUseCases(
            resendCode: ResendCodeUseCase,
            verifyCode: VerifyCodeUseCase
        ): CodesentUseCases = CodesentUseCases(
            resendCode = resendCode,
            verifyCode = verifyCode
        )
    }
}