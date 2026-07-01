package com.williamsel.mathstack.features.auth.login.data.repositories

import android.content.Context
import com.williamsel.mathstack.features.auth.login.data.datasource.api.GoogleAuthUiClient
import com.williamsel.mathstack.features.auth.login.data.datasource.api.LoginApi
import com.williamsel.mathstack.features.auth.login.data.mapper.toDomain
import com.williamsel.mathstack.features.auth.login.domain.entities.GoogleUser
import com.williamsel.mathstack.features.auth.login.domain.repository.LoginRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val googleAuthUiClient: GoogleAuthUiClient,
    @ApplicationContext private val context: Context
) : LoginRepository {

    // Reemplaza con el Web Client ID de tu proyecto en Firebase / Google Cloud Console
    private val webClientId: String =
        "TU_WEB_CLIENT_ID.apps.googleusercontent.com"

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            loginApi.login(email, password).toDomain()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(): Result<Unit> {
        return try {
            val googleResult = googleAuthUiClient.signIn(
                activityContext = context,
                webClientId = webClientId
            )
            val googleUser: GoogleUser = googleResult.getOrElse {
                return Result.failure(it)
            }
            loginApi.loginWithGoogle(googleUser.idToken).toDomain()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
