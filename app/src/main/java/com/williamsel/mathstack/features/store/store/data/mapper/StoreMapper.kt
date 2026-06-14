package com.williamsel.mathstack.features.private.store.data.mapper

import com.williamsel.mathstack.features.private.store.data.models.AvatarDto
import com.williamsel.mathstack.features.private.store.data.models.StoreDto
import com.williamsel.mathstack.features.private.store.domain.entities.Avatar
import com.williamsel.mathstack.features.private.store.domain.entities.Store

fun StoreDto.toDomain(): Store = Store(
    coinBalance = coinBalance,
    avatars     = avatars.map { it.toDomain() }
)

fun AvatarDto.toDomain(): Avatar = Avatar(
    id         = id,
    name       = name,
    emoji      = emoji,
    price      = price,
    isOwned    = isOwned,
    isEquipped = isEquipped
)