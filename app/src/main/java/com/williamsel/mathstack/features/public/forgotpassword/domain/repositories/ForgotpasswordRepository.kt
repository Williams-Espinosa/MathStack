package com.williamsel.mathstack.features.public.forgotpassword.domain.repositories

interface ForgotpasswordRepository {
    suspend fun sendResetCode(email: String): Result<Unit>
    suspend fun verifyCode(email: String, code: String): Result<Unit>
    suspend fun resetPassword(email: String, newPassword: String): Result<Unit>
}