package com.williamsel.mathstack.features.private.store.data.models

import com.google.gson.annotations.SerializedName

data class StoreDto(
    @SerializedName("coin_balance")
    val coinBalance: Int,

    @SerializedName("avatars")
    val avatars: List<AvatarDto>
)

data class AvatarDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("emoji")
    val emoji: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("is_owned")
    val isOwned: Boolean,

    @SerializedName("is_equipped")
    val isEquipped: Boolean = false
)