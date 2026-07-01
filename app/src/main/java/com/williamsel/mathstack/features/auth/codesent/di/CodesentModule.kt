package com.williamsel.mathstack.features.auth.codesent.di

import com.williamsel.mathstack.features.auth.codesent.data.datasource.api.CodesentApi
import com.williamsel.mathstack.features.auth.codesent.data.repositories.CodesentRepositoryImpl
import com.williamsel.mathstack.features.auth.codesent.domain.repository.CodesentRepository
import com.williamsel.mathstack.features.auth.codesent.domain.usecase.CodesentUseCases
import com.williamsel.mathstack.features.auth.codesent.domain.usecase.ResendCodeUseCase
import com.williamsel.mathstack.features.auth.codesent.domain.usecase.VerifyCodeUseCase
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
