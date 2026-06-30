package com.williamsel.mathstack.features.store.domain.entities

data class Store(
    val coinBalance: Int,
    val avatars: List<Avatar>
)
data class Avatar(
    val id: String,
    val name: String,
    val emoji: String,
    val price: Int,
    val isOwned: Boolean,
    val isEquipped: Boolean = false
)