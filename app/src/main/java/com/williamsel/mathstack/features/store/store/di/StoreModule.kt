package com.williamsel.mathstack.features.store.di

import com.williamsel.mathstack.features.store.data.datasource.api.StoreApi
import com.williamsel.mathstack.features.store.data.repositories.StoreRepositoryImpl
import com.williamsel.mathstack.features.store.domain.repositories.StoreRepository
import com.williamsel.mathstack.features.store.domain.usecases.EquipAvatarUseCase
import com.williamsel.mathstack.features.store.domain.usecases.GetStoreUseCase
import com.williamsel.mathstack.features.store.domain.usecases.PurchaseAvatarUseCase
import com.williamsel.mathstack.features.store.domain.usecases.StoreUseCases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {

    @Binds
    @Singleton
    abstract fun bindStoreRepository(
        impl: StoreRepositoryImpl
    ): StoreRepository

    companion object {

        @Provides
        @Singleton
        fun provideStoreApi(retrofit: Retrofit): StoreApi =
            retrofit.create(StoreApi::class.java)

        @Provides
        @Singleton
        fun provideStoreUseCases(
            getStore: GetStoreUseCase,
            purchaseAvatar: PurchaseAvatarUseCase,
            equipAvatar: EquipAvatarUseCase
        ): StoreUseCases = StoreUseCases(
            getStore       = getStore,
            purchaseAvatar = purchaseAvatar,
            equipAvatar    = equipAvatar
        )
    }
}
