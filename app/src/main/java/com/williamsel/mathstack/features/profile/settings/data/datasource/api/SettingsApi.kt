package com.williamsel.mathstack.features.private.settings.data.datasource.api

import com.williamsel.mathstack.features.private.settings.data.models.SettingsDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface SettingsApi {

    @GET("settings")
    suspend fun getSettings(): SettingsDto

    @PUT("settings")
    suspend fun updateSettings(@Body settings: SettingsDto): SettingsDto

    @POST("auth/logout")
    suspend fun logout()
}