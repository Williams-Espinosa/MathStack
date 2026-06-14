package com.williamsel.mathstack.features.private.store.domain.usecases

import com.williamsel.mathstack.features.private.store.domain.entities.Store
import com.williamsel.mathstack.features.private.store.domain.repositories.StoreRepository
import javax.inject.Inject
class GetStoreUseCase @Inject constructor(
    private val repository: StoreRepository
) {
    suspend operator fun invoke(): Result<Store> = repository.getStore()
}

class PurchaseAvatarUseCase @Inject constructor(
    private val repository: StoreRepository
) {
    suspend operator fun invoke(store: Store, avatarId: String): Result<Store> {
        val avatar = store.avatars.find { it.id == avatarId }
            ?: return Result.failure(IllegalArgumentException("Avatar no encontrado"))

        if (avatar.isOwned)
            return Result.failure(IllegalStateException("Ya tienes este avatar"))

        if (store.coinBalance < avatar.price)
            return Result.failure(IllegalStateException("No tienes suficientes monedas"))

        return repository.purchaseAvatar(avatarId)
    }
}

class EquipAvatarUseCase @Inject constructor(
    private val repository: StoreRepository
) {
    suspend operator fun invoke(store: Store, avatarId: String): Result<Store> {
        val avatar = store.avatars.find { it.id == avatarId }
            ?: return Result.failure(IllegalArgumentException("Avatar no encontrado"))

        if (!avatar.isOwned)
            return Result.failure(IllegalStateException("Debes comprar este avatar antes de equiparlo"))

        if (avatar.isEquipped)
            return Result.success(store)

        return repository.equipAvatar(avatarId)
    }
}

data class StoreUseCases(
    val getStore: GetStoreUseCase,
    val purchaseAvatar: PurchaseAvatarUseCase,
    val equipAvatar: EquipAvatarUseCase
)