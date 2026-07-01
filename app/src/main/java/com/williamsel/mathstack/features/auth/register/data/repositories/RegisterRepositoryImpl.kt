package com.williamsel.mathstack.features.auth.register.data.repositories

import android.content.Context
import com.williamsel.mathstack.features.auth.register.data.datasource.api.GoogleAuthUiClient
import com.williamsel.mathstack.features.auth.register.data.datasource.api.RegisterApi
import com.williamsel.mathstack.features.auth.register.data.mapper.toDomain
import com.williamsel.mathstack.features.auth.register.domain.entities.GoogleUser
import com.williamsel.mathstack.features.auth.register.domain.repositories.RegisterRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerApi: RegisterApi,
    private val googleAuthUiClient: GoogleAuthUiClient,
    @ApplicationContext private val context: Context
) : RegisterRepository {
    private val webClientId = "TU_WEB_CLIENT_ID.apps.googleusercontent.com"

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            registerApi.register(username, email, password).toDomain()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerWithGoogle(): Result<Unit> {
        return try {
            val googleResult = googleAuthUiClient.signIn(
                activityContext = context,
                webClientId = webClientId
            )
            val googleUser: GoogleUser = googleResult.getOrElse {
                return Result.failure(it)
            }
            registerApi.registerWithGoogle(googleUser.idToken).toDomain()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
