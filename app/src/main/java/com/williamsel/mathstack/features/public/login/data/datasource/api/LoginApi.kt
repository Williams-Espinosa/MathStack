package com.williamsel.mathstack.features.public.login.data.datasource.api

import com.williamsel.mathstack.features.public.login.data.models.LoginDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email")    email: String,
        @Field("password") password: String
    ): LoginDto

    @FormUrlEncoded
    @POST("auth/google")
    suspend fun loginWithGoogle(
        @Field("id_token") idToken: String
    ): LoginDto
}