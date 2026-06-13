package com.williamsel.mathstack.features.public.forgotpassword.data.datasource.api

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.ClearCredentialStateRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleAuthUiClient @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun signOut(): Result<Unit> {
        return try {
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}