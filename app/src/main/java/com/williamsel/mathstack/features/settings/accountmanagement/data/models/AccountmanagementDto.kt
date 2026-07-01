package com.williamsel.mathstack.features.settings.accountmanagement.data.models

import com.google.gson.annotations.SerializedName

data class AccountmanagementDto(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)

data class UpdateUsernameRequestDto(
    @SerializedName("username") val username: String
)
