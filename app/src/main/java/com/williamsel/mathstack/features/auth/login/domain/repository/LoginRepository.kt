package com.williamsel.mathstack.features.auth.login.domain.repository

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun loginWithGoogle(): Result<Unit>
}
