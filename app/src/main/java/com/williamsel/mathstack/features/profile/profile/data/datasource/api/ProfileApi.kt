package com.williamsel.mathstack.features.profile.profile.data.datasource.api

import com.williamsel.mathstack.features.profile.profile.data.models.ProfileDto
import retrofit2.http.GET

interface ProfileApi {

    @GET("profile")
    suspend fun getProfile(): ProfileDto
}