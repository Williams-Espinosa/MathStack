package com.williamsel.mathstack.features.auth.login.domain.usecase

import com.williamsel.mathstack.features.auth.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(Exception("El correo no puede estar vacío"))
        }
        if (password.isBlank()) {
            return Result.failure(Exception("La contraseña no puede estar vacía"))
        }
        return repository.login(email, password)
    }
}
