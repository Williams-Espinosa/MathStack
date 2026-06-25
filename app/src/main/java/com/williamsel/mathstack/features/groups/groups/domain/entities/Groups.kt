package com.williamsel.mathstack.features.private.groups.domain.entities

data class Groups(
    val myGroupsCount: Int,
    val groups: List<Group>
)
data class Group(
    val id: String,
    val name: String,
    val subject: String,
    val level: String,
    val membersCount: Int,
    val activeChallenges: Int,
    val colorHex: String,
    val emoji: String,
    val isOwner: Boolean = false
)