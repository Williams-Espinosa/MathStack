package com.williamsel.mathstack.features.public.codesent.domain.repositories

interface CodesentRepository {
    suspend fun resendCode(email: String): Result<Unit>
    suspend fun verifyCode(email: String, code: String): Result<Unit>
}