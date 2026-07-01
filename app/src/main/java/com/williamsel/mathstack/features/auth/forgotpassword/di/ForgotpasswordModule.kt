package com.williamsel.mathstack.features.auth.forgotpassword.di

import com.williamsel.mathstack.features.auth.forgotpassword.data.datasource.api.ForgotpasswordApi
import com.williamsel.mathstack.features.auth.forgotpassword.data.repositories.ForgotpasswordRepositoryImpl
import com.williamsel.mathstack.features.auth.forgotpassword.domain.repository.ForgotpasswordRepository
import com.williamsel.mathstack.features.auth.forgotpassword.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ForgotpasswordModule {

    @Binds
    @Singleton
    abstract fun bindForgotpasswordRepository(
        impl: ForgotpasswordRepositoryImpl
    ): ForgotpasswordRepository

    companion object {

        @Provides
        @Singleton
        fun provideForgotpasswordApi(retrofit: Retrofit): ForgotpasswordApi =
            retrofit.create(ForgotpasswordApi::class.java)

        @Provides
        @Singleton
        fun provideForgotpasswordUseCases(
            sendResetCode: SendResetCodeUseCase,
            verifyResetCode: VerifyResetCodeUseCase,
            resetPassword: ResetPasswordUseCase
        ): ForgotpasswordUseCases = ForgotpasswordUseCases(
            sendResetCode   = sendResetCode,
            verifyResetCode = verifyResetCode,
            resetPassword   = resetPassword
        )
    }
}
