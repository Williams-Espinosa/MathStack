package com.williamsel.mathstack.features.public.login.data.models

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("display_name")
    val displayName: String?,

    @SerializedName("photo_url")
    val photoUrl: String?,

    @SerializedName("email_verified")
    val emailVerified: Boolean = false,

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String
)