package com.williamsel.mathstack.features.private.groups.data.models

import com.google.gson.annotations.SerializedName

data class GroupsDto(
    @SerializedName("my_groups_count")
    val myGroupsCount: Int,

    @SerializedName("groups")
    val groups: List<GroupDto>
)

data class GroupDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("subject")
    val subject: String,

    @SerializedName("level")
    val level: String,

    @SerializedName("members_count")
    val membersCount: Int,

    @SerializedName("active_challenges")
    val activeChallenges: Int,

    @SerializedName("color_hex")
    val colorHex: String,

    @SerializedName("emoji")
    val emoji: String,

    @SerializedName("is_owner")
    val isOwner: Boolean = false
)