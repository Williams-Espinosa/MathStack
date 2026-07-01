package com.williamsel.mathstack.features.auth.register.data.models

import com.google.gson.annotations.SerializedName

data class RegisterDto(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("username")
    val username: String,

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
