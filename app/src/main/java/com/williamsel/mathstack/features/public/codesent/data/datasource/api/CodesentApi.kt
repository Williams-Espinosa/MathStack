package com.williamsel.mathstack.features.public.codesent.data.datasource.api

import com.williamsel.mathstack.features.public.codesent.data.models.CodesentDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CodesentApi {

    @FormUrlEncoded
    @POST("auth/resend-code")
    suspend fun resendCode(
        @Field("email") email: String
    ): CodesentDto

    @FormUrlEncoded
    @POST("auth/verify-code")
    suspend fun verifyCode(
        @Field("email") email: String,
        @Field("code")  code: String
    ): CodesentDto
}