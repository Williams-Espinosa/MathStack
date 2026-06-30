package com.williamsel.mathstack.features.challenges.exercises.data.datasource.api
import  com.williamsel.mathstack.features.challenges.exercises.data.models.CheckAnswerResponseDto
import  com.williamsel.mathstack.features.challenges.exercises.data.models.LearningPathDto
import  com.williamsel.mathstack.features.challenges.exercises.data.models.LessonTheoryDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ExercisesApi {

    @GET("learning-paths/{pathId}")
    suspend fun getLearningPath(
        @Path("pathId") pathId: String
    ): LearningPathDto

    @GET("lessons/{lessonId}/theory")
    suspend fun getLessonTheory(
        @Path("lessonId") lessonId: String
    ): LessonTheoryDto

    @POST("exercises/{exerciseId}/check")
    suspend fun checkAnswer(
        @Path("exerciseId") exerciseId: String,
        @Body body: CheckAnswerRequestBody
    ): CheckAnswerResponseDto

    @PUT("lessons/{lessonId}/complete")
    suspend fun completeLesson(
        @Path("lessonId") lessonId: String,
        @Body body: CompleteLessonRequestBody
    )
}

data class CheckAnswerRequestBody(val answer: String)
data class CompleteLessonRequestBody(val earnedXp: Int)
