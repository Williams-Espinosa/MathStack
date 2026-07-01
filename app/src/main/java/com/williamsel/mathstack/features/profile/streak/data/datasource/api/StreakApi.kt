package com.williamsel.mathstack.features.profile.streak.data.datasource.api

import com.williamsel.mathstack.features.profile.streak.data.models.StreakDto
import retrofit2.http.GET

interface StreakApi {

    @GET("streak")
    suspend fun getStreak(): StreakDto
}