package com.williamsel.mathstack.features.private.groupranking.data.mapper

import com.williamsel.mathstack.features.private.groupranking.data.models.*
import com.williamsel.mathstack.features.private.groupranking.domain.entities.*

fun GroupRankingDto.toDomain(): GroupRanking = GroupRanking(
    groupInfo     = groupInfo.toDomain(),
    members       = members.map { it.toDomain() },
    currentUserId = currentUserId
)

fun GroupInfoDto.toDomain(): GroupInfo = GroupInfo(
    id               = id,
    name             = name,
    subject          = subject,
    level            = level,
    membersCount     = membersCount,
    activeChallenges = activeChallenges,
    totalXp          = totalXp
)

fun GroupMemberRankDto.toDomain(): GroupMemberRank = GroupMemberRank(
    userId           = userId,
    name             = name,
    rank             = rank,
    level            = level,
    streakDays       = streakDays,
    lessonsCompleted = lessonsCompleted,
    xp               = xp,
    badge            = badge.toRankBadge()
)


fun GlobalRankingDto.toDomain(): GlobalRanking = GlobalRanking(
    players       = players.map { it.toDomain() },
    currentUserId = currentUserId
)

fun GlobalPlayerRankDto.toDomain(): GlobalPlayerRank = GlobalPlayerRank(
    userId     = userId,
    name       = name,
    rank       = rank,
    level      = level,
    streakDays = streakDays,
    xp         = xp,
    badge      = badge.toRankBadge()
)

private fun String?.toRankBadge(): RankBadge = when (this) {
    "gold"   -> RankBadge.GOLD
    "silver" -> RankBadge.SILVER
    "bronze" -> RankBadge.BRONZE
    else     -> RankBadge.NONE
}
