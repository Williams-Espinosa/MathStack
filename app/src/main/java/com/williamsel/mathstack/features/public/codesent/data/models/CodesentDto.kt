package com.williamsel.mathstack.features.public.codesent.data.models

import com.google.gson.annotations.SerializedName

data class CodesentDto(
    @SerializedName("email")
    val email: String,

    @SerializedName("message")
    val message: String
)