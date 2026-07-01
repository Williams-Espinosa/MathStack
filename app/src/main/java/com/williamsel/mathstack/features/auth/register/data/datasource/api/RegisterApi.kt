package com.williamsel.mathstack.features.auth.register.data.datasource.api

import com.williamsel.mathstack.features.auth.register.data.models.RegisterDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterApi {
    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email")    email: String,
        @Field("password") password: String
    ): RegisterDto

    @FormUrlEncoded
    @POST("auth/google")
    suspend fun registerWithGoogle(
        @Field("id_token") idToken: String
    ): RegisterDto
}
