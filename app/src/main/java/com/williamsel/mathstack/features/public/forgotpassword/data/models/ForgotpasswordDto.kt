package com.williamsel.mathstack.features.public.forgotpassword.data.models

import com.google.gson.annotations.SerializedName

data class ForgotpasswordDto(
    @SerializedName("email")
    val email: String,

    @SerializedName("message")
    val message: String
)