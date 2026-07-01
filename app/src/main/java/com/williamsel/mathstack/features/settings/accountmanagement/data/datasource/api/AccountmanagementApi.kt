package com.williamsel.mathstack.features.settings.accountmanagement.data.datasource.api

import com.williamsel.mathstack.features.settings.accountmanagement.data.models.AccountmanagementDto
import com.williamsel.mathstack.features.settings.accountmanagement.data.models.UpdateUsernameRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface AccountmanagementApi {

    @GET("account/me")
    suspend fun getAccountInfo(): Response<AccountmanagementDto>

    @PUT("account/username")
    suspend fun updateUsername(@Body request: UpdateUsernameRequestDto): Response<Unit>

    @DELETE("account/me")
    suspend fun deleteAccount(): Response<Unit>
}
