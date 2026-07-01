package com.williamsel.mathstack.features.store.data.datasource.api

import com.williamsel.mathstack.features.store.data.models.StoreDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StoreApi {

    @GET("store")
    suspend fun getStore(): StoreDto

    @POST("store/avatars/{avatarId}/purchase")
    suspend fun purchaseAvatar(@Path("avatarId") avatarId: String): StoreDto

    @POST("store/avatars/{avatarId}/equip")
    suspend fun equipAvatar(@Path("avatarId") avatarId: String): StoreDto
}