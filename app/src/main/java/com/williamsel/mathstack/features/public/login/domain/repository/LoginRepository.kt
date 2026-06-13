package com.williamsel.mathstack.features.public.login.domain.repository

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun loginWithGoogle(): Result<Unit>
}
