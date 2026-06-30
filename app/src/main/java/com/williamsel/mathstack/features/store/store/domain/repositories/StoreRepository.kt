package com.williamsel.mathstack.features.store.domain.repositories

import com.williamsel.mathstack.features.store.domain.entities.Store

interface StoreRepository {
    suspend fun getStore(): Result<Store>
    suspend fun purchaseAvatar(avatarId: String): Result<Store>
    suspend fun equipAvatar(avatarId: String): Result<Store>
}