package com.williamsel.mathstack.features.auth.forgotpassword.data.models

import com.google.gson.annotations.SerializedName

data class ForgotpasswordDto(
    @SerializedName("email")
    val email: String,

    @SerializedName("message")
    val message: String
)
