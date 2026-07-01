package com.williamsel.mathstack.features.groupranking.data.models

import com.google.gson.annotations.SerializedName

data class GroupRankingDto(
    @SerializedName("group_info")
    val groupInfo: GroupInfoDto,

    @SerializedName("members")
    val members: List<GroupMemberRankDto>,

    @SerializedName("current_user_id")
    val currentUserId: String
)

data class GroupInfoDto(
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

    @SerializedName("total_xp")
    val totalXp: Int
)

data class GroupMemberRankDto(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("rank")
    val rank: Int,

    @SerializedName("level")
    val level: Int,

    @SerializedName("streak_days")
    val streakDays: Int,

    @SerializedName("lessons_completed")
    val lessonsCompleted: Int,

    @SerializedName("xp")
    val xp: Int,

    @SerializedName("badge")
    val badge: String?
)


data class GlobalRankingDto(
    @SerializedName("players")
    val players: List<GlobalPlayerRankDto>,

    @SerializedName("current_user_id")
    val currentUserId: String
)

data class GlobalPlayerRankDto(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("rank")
    val rank: Int,

    @SerializedName("level")
    val level: Int,

    @SerializedName("streak_days")
    val streakDays: Int,

    @SerializedName("xp")
    val xp: Int,

    @SerializedName("badge")
    val badge: String?
)
