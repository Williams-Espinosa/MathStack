package com.williamsel.mathstack.features.auth.register.domain.repositories

interface RegisterRepository {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Unit>
    suspend fun registerWithGoogle(): Result<Unit>
}
