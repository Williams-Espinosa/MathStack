package com.williamsel.mathstack.features.private.creategroups.data.models

import com.google.gson.annotations.SerializedName

data class CreateGroupRequestDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("subject")
    val subject: String,

    @SerializedName("max_members")
    val maxMembers: Int
)

data class CreatedGroupDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("subject")
    val subject: String,

    @SerializedName("max_members")
    val maxMembers: Int
)
