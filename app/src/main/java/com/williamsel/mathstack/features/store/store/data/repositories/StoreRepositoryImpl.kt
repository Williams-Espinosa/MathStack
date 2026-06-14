package com.williamsel.mathstack.features.private.store.data.repositories

import com.williamsel.mathstack.features.private.store.data.datasource.api.StoreApi
import com.williamsel.mathstack.features.private.store.data.mapper.toDomain
import com.williamsel.mathstack.features.private.store.domain.entities.Store
import com.williamsel.mathstack.features.private.store.domain.repositories.StoreRepository
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val api: StoreApi
) : StoreRepository {

    override suspend fun getStore(): Result<Store> = runCatching {
        api.getStore().toDomain()
    }

    override suspend fun purchaseAvatar(avatarId: String): Result<Store> = runCatching {
        api.purchaseAvatar(avatarId).toDomain()
    }

    override suspend fun equipAvatar(avatarId: String): Result<Store> = runCatching {
        api.equipAvatar(avatarId).toDomain()
    }
}