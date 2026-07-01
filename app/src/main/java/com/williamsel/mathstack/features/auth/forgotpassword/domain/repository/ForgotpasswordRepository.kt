package com.williamsel.mathstack.features.auth.forgotpassword.domain.repository

interface ForgotpasswordRepository {
    suspend fun sendResetCode(email: String): Result<Unit>
    suspend fun verifyCode(email: String, code: String): Result<Unit>
    suspend fun resetPassword(email: String, newPassword: String): Result<Unit>
}
