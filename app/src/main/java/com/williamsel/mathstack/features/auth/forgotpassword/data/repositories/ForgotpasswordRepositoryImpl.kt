package com.williamsel.mathstack.features.auth.forgotpassword.data.repositories

import com.williamsel.mathstack.features.auth.forgotpassword.data.datasource.api.ForgotpasswordApi
import com.williamsel.mathstack.features.auth.forgotpassword.data.mapper.toDomain
import com.williamsel.mathstack.features.auth.forgotpassword.domain.repository.ForgotpasswordRepository
import javax.inject.Inject

class ForgotpasswordRepositoryImpl @Inject constructor(
    private val api: ForgotpasswordApi
) : ForgotpasswordRepository {

    override suspend fun sendResetCode(email: String): Result<Unit> = runCatching {
        api.sendResetCode(email).toDomain()
    }.map { }

    override suspend fun verifyCode(email: String, code: String): Result<Unit> = runCatching {
        api.verifyCode(email, code).toDomain()
    }.map { }

    override suspend fun resetPassword(email: String, newPassword: String): Result<Unit> = runCatching {
        api.resetPassword(email, newPassword).toDomain()
    }.map { }
}
