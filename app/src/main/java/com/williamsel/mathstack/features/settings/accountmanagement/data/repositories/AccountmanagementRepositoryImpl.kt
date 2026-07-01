package com.williamsel.mathstack.features.settings.accountmanagement.data.repositories

import com.williamsel.mathstack.features.settings.accountmanagement.data.datasource.api.AccountmanagementApi
import com.williamsel.mathstack.features.settings.accountmanagement.data.mapper.toDomain
import com.williamsel.mathstack.features.settings.accountmanagement.data.models.UpdateUsernameRequestDto
import com.williamsel.mathstack.features.settings.accountmanagement.domain.entities.Accountmanagement
import com.williamsel.mathstack.features.settings.accountmanagement.domain.repositories.AccountmanagementRepository
import javax.inject.Inject

class AccountmanagementRepositoryImpl @Inject constructor(
    private val api: AccountmanagementApi
) : AccountmanagementRepository {

    override suspend fun getAccountInfo(): Result<Accountmanagement> {
        return try {
            val response = api.getAccountInfo()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomain())
                } ?: Result.failure(Exception("Respuesta vacía del servidor"))
            } else {
                Result.failure(Exception("Error al obtener la información de la cuenta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUsername(username: String): Result<Unit> {
        return try {
            val response = api.updateUsername(UpdateUsernameRequestDto(username))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al actualizar el nombre de usuario: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return try {
            val response = api.deleteAccount()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al eliminar la cuenta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
