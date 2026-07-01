package com.williamsel.mathstack.features.settings.accountmanagement.domain.usecases

import com.williamsel.mathstack.features.settings.accountmanagement.domain.entities.Accountmanagement
import com.williamsel.mathstack.features.settings.accountmanagement.domain.repositories.AccountmanagementRepository
import javax.inject.Inject

class GetAccountInfoUseCase @Inject constructor(
    private val repository: AccountmanagementRepository
) {
    suspend operator fun invoke(): Result<Accountmanagement> = repository.getAccountInfo()
}

class UpdateUsernameUseCase @Inject constructor(
    private val repository: AccountmanagementRepository
) {
    suspend operator fun invoke(username: String): Result<Unit> {
        if (username.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre de usuario no puede estar vacío"))
        }
        return repository.updateUsername(username)
    }
}

class DeleteAccountUseCase @Inject constructor(
    private val repository: AccountmanagementRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.deleteAccount()
}

data class AccountmanagementUseCases @Inject constructor(
    val getAccountInfo: GetAccountInfoUseCase,
    val updateUsername: UpdateUsernameUseCase,
    val deleteAccount: DeleteAccountUseCase
)
