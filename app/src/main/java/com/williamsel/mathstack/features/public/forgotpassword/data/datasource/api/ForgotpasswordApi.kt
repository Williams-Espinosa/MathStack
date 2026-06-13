package com.williamsel.mathstack.features.public.forgotpassword.data.datasource.api

import com.williamsel.mathstack.features.public.forgotpassword.data.models.ForgotpasswordDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ForgotpasswordApi {

    @FormUrlEncoded
    @POST("auth/forgot-password")
    suspend fun sendResetCode(
        @Field("email") email: String
    ): ForgotpasswordDto

    @FormUrlEncoded
    @POST("auth/verify-code")
    suspend fun verifyCode(
        @Field("email") email: String,
        @Field("code")  code: String
    ): ForgotpasswordDto

    @FormUrlEncoded
    @POST("auth/reset-password")
    suspend fun resetPassword(
        @Field("email")        email: String,
        @Field("new_password") newPassword: String
    ): ForgotpasswordDto
}