package com.williamsel.mathstack.core.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.core.domain.model.NetworkStatus
import com.williamsel.mathstack.core.domain.usecase.ObserveConnectivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    observeConnectivity: ObserveConnectivityUseCase
) : ViewModel() {

    val networkStatus: StateFlow<NetworkStatus> = observeConnectivity()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NetworkStatus.Available
        )
}
