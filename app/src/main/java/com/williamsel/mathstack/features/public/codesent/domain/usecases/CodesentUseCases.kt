package com.williamsel.mathstack.features.public.codesent.domain.usecases

import com.williamsel.mathstack.features.public.codesent.domain.repositories.CodesentRepository
import javax.inject.Inject

class ResendCodeUseCase @Inject constructor(
    private val repository: CodesentRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return Result.failure(IllegalArgumentException("Correo electrónico inválido"))
        return repository.resendCode(email)
    }
}

class VerifyCodeUseCase @Inject constructor(
    private val repository: CodesentRepository
) {
    suspend operator fun invoke(email: String, code: String): Result<Unit> {
        if (code.length != 6 || !code.all { it.isDigit() })
            return Result.failure(IllegalArgumentException("El código debe tener 6 dígitos"))
        return repository.verifyCode(email, code)
    }
}

data class CodesentUseCases(
    val resendCode: ResendCodeUseCase,
    val verifyCode: VerifyCodeUseCase
)