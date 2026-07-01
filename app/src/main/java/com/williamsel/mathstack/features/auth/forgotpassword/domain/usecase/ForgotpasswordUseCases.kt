package com.williamsel.mathstack.features.auth.forgotpassword.domain.usecase

import com.williamsel.mathstack.features.auth.forgotpassword.domain.repository.ForgotpasswordRepository
import javax.inject.Inject

class SendResetCodeUseCase @Inject constructor(
    private val repository: ForgotpasswordRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank())
            return Result.failure(IllegalArgumentException("El correo no puede estar vacío"))
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return Result.failure(IllegalArgumentException("Correo electrónico inválido"))
        return repository.sendResetCode(email)
    }
}

class VerifyResetCodeUseCase @Inject constructor(
    private val repository: ForgotpasswordRepository
) {
    suspend operator fun invoke(email: String, code: String): Result<Unit> {
        if (code.length != 6 || !code.all { it.isDigit() })
            return Result.failure(IllegalArgumentException("El código debe tener 6 dígitos"))
        return repository.verifyCode(email, code)
    }
}

class ResetPasswordUseCase @Inject constructor(
    private val repository: ForgotpasswordRepository
) {
    suspend operator fun invoke(email: String, newPassword: String): Result<Unit> {
        if (newPassword.length < 8)
            return Result.failure(IllegalArgumentException("La contraseña debe tener al menos 8 caracteres"))
        return repository.resetPassword(email, newPassword)
    }
}

data class ForgotpasswordUseCases(
    val sendResetCode: SendResetCodeUseCase,
    val verifyResetCode: VerifyResetCodeUseCase,
    val resetPassword: ResetPasswordUseCase
)
