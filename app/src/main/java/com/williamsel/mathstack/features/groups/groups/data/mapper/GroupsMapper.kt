package com.williamsel.mathstack.features.groups.data.mapper

import com.williamsel.mathstack.features.groups.data.models.GroupDto
import com.williamsel.mathstack.features.groups.data.models.GroupsDto
import com.williamsel.mathstack.features.groups.domain.entities.Group
import com.williamsel.mathstack.features.groups.domain.entities.Groups

fun GroupsDto.toDomain(): Groups = Groups(
    myGroupsCount = myGroupsCount,
    groups        = groups.map { it.toDomain() }
)

fun GroupDto.toDomain(): Group = Group(
    id               = id,
    name             = name,
    subject          = subject,
    level            = level,
    membersCount     = membersCount,
    activeChallenges = activeChallenges,
    colorHex         = colorHex,
    emoji            = emoji,
    isOwner          = isOwner
)