package com.tuapp.weeklychallenges.data.datasource.api

import com.tuapp.weeklychallenges.data.models.JoinChallengeResponseDto
import com.tuapp.weeklychallenges.data.models.SubmitAnswerResponseDto
import com.tuapp.weeklychallenges.data.models.WeeklyChallengeDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WeeklychallengesApi {

    @GET("challenges/weekly")
    suspend fun getChallenges(): List<WeeklyChallengeDto>

    @POST("challenges/weekly/{id}/join")
    suspend fun joinChallenge(
        @Path("id") challengeId: String
    ): JoinChallengeResponseDto

    @POST("challenges/weekly/{id}/exercises/{exerciseId}/answer")
    suspend fun submitAnswer(
        @Path("id") challengeId: String,
        @Path("exerciseId") exerciseId: String,
        @Body body: SubmitAnswerRequestBody
    ): SubmitAnswerResponseDto

    @PUT("challenges/weekly/{id}/progress")
    suspend fun saveProgress(
        @Path("id") challengeId: String,
        @Body body: SaveProgressRequestBody
    )
}

data class SubmitAnswerRequestBody(val optionId: String)

data class SaveProgressRequestBody(
    val progressFraction: Float,
    val earnedXp: Int,
    val earnedCoins: Int
)
