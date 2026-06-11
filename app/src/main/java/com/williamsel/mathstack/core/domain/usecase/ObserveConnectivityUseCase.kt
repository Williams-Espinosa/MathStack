package com.williamsel.mathstack.core.domain.usecase

import com.williamsel.mathstack.core.domain.model.NetworkStatus
import com.williamsel.mathstack.core.domain.repository.IConnectivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveConnectivityUseCase @Inject constructor(
    private val repo: IConnectivityRepository
) {
    operator fun invoke(): Flow<NetworkStatus> = repo.observeConnectivity()
}
