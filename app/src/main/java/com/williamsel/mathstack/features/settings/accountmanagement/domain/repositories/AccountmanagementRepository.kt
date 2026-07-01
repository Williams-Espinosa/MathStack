package com.williamsel.mathstack.features.settings.accountmanagement.domain.repositories

import com.williamsel.mathstack.features.settings.accountmanagement.domain.entities.Accountmanagement

interface AccountmanagementRepository {

    suspend fun getAccountInfo(): Result<Accountmanagement>

    suspend fun updateUsername(username: String): Result<Unit>

    suspend fun deleteAccount(): Result<Unit>
}
