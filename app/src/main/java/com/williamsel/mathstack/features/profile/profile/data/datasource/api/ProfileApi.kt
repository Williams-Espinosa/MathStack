package com.williamsel.mathstack.features.private.profile.data.datasource.api

import com.williamsel.mathstack.features.private.profile.data.models.ProfileDto
import retrofit2.http.GET

interface ProfileApi {

    @GET("profile")
    suspend fun getProfile(): ProfileDto
}