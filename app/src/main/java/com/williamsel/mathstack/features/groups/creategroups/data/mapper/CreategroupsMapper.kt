package com.williamsel.mathstack.features.private.creategroups.data.mapper

import com.williamsel.mathstack.features.private.creategroups.data.models.CreatedGroupDto
import com.williamsel.mathstack.features.private.creategroups.domain.entities.CreatedGroup
import com.williamsel.mathstack.features.private.creategroups.domain.entities.CreateGroupParams
import com.williamsel.mathstack.features.private.creategroups.data.models.CreateGroupRequestDto

fun CreateGroupParams.toDto(): CreateGroupRequestDto = CreateGroupRequestDto(
    name        = name,
    description = description,
    subject     = subject,
    maxMembers  = maxMembers
)

fun CreatedGroupDto.toDomain(): CreatedGroup = CreatedGroup(
    id          = id,
    name        = name,
    description = description,
    subject     = subject,
    maxMembers  = maxMembers
)
