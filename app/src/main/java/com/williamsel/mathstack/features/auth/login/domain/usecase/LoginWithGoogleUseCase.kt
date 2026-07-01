package com.williamsel.mathstack.features.auth.login.domain.usecase

import com.williamsel.mathstack.features.auth.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.loginWithGoogle()
    }
}
