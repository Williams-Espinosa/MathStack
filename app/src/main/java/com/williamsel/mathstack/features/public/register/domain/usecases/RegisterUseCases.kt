package com.williamsel.mathstack.features.public.register.domain.usecases

import com.williamsel.mathstack.features.public.register.domain.repositories.RegisterRepository
import javax.inject.Inject
class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<Unit> {
        if (username.isBlank())
            return Result.failure(IllegalArgumentException("El nombre de usuario no puede estar vacío"))

        if (username.contains(" "))
            return Result.failure(IllegalArgumentException("El nombre de usuario no puede contener espacios"))

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return Result.failure(IllegalArgumentException("Correo electrónico inválido"))

        if (password.length < 8)
            return Result.failure(IllegalArgumentException("La contraseña debe tener al menos 8 caracteres"))

        if (password != confirmPassword)
            return Result.failure(IllegalArgumentException("Las contraseñas no coinciden"))

        return repository.register(username, email, password)
    }
}
class RegisterWithGoogleUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.registerWithGoogle()
    }
}