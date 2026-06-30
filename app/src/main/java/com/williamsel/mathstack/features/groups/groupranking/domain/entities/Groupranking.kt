package com.williamsel.mathstack.features.private.groupranking.domain.entities


data class GroupRanking(
    val groupInfo: GroupInfo,
    val members: List<GroupMemberRank>,
    val currentUserId: String
)

data class GroupInfo(
    val id: String,
    val name: String,
    val subject: String,
    val level: String,
    val membersCount: Int,
    val activeChallenges: Int,
    val totalXp: Int
)

data class GroupMemberRank(
    val userId: String,
    val name: String,
    val rank: Int,
    val level: Int,
    val streakDays: Int,
    val lessonsCompleted: Int,
    val xp: Int,
    val badge: RankBadge
)


data class GlobalRanking(
    val players: List<GlobalPlayerRank>,
    val currentUserId: String
)

data class GlobalPlayerRank(
    val userId: String,
    val name: String,
    val rank: Int,
    val level: Int,
    val streakDays: Int,
    val xp: Int,
    val badge: RankBadge
)

// ── Shared ────────────────────────────────────────────────────────────────────

enum class RankBadge { GOLD, SILVER, BRONZE, NONE }
