package com.williamsel.mathstack.features.auth.codesent.domain.repository

interface CodesentRepository {
    suspend fun resendCode(email: String): Result<Unit>
    suspend fun verifyCode(email: String, code: String): Result<Unit>
}
