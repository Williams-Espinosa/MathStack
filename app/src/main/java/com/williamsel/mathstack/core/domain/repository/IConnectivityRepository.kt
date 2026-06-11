package com.williamsel.mathstack.core.domain.repository

import com.williamsel.mathstack.core.domain.model.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface IConnectivityRepository {
    fun observeConnectivity(): Flow<NetworkStatus>
}
